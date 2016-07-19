<?php
defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

class Api extends REST_Controller
{

	public function __construct()
	{
		parent::__construct();
		$this->load->database();
		$this->load->model('device_model');
		$this->load->model('sensor_model');
		$this->load->model('user_model');
		$this->load->model('datapoint_model');
	}

	/**
	 * create a new device with info posted
	 * we should check some columns to ensure the data validation
	 * `name` required
	 * `user_id` valid, check by database system foreign key
	 * of course, we should check apikey first
	 */
	public function devices_post()
	{
		$user_id = $this->_check_apikey();
		
		if ($this->post('name') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `name` required'
			), 400);
		}
		
		$input = array (
				'id' => NULL,
				'name' => $this->post('name'),
				'tags' => ($this->post('tags') == FALSE) ? NULL : $this->post('tags'),
				'about' => ($this->post('about') == FALSE) ? NULL : $this->post('about'),
				'locate' => ($this->post('locate') == FALSE) ? NULL : $this->post('locate'),
				'user_id' => $user_id,
				'create_time' => time(),
				'last_active' => NULL,
				'status' => 1
		);
		$result = $this->device_model->create($input);
		if ($result === FALSE)
		{
			$this->response(array('info' => "Create Device fail"), 400);
		}
		else
		{
			$data = array (
					'info' => "Create Device `$result` success",
					'device_id' => $result
			);
			$this->response($data, 200);
		}
	}

	/**
	 * Get devices list owned by the user
	 * get userid by apikey
	 */
	public function devices_get()
	{
		$user_id = $this->_check_apikey();
		
		$data = $this->device_model->get_devices($user_id);
		if ($data === FALSE)
			$this->response(array (
					'info' => 'no device found under this user'
			), 400);
		else
			$this->response($data, 200);
	}

	/**
	 * get info of a specific device by device_id
	 * we should also check user permission and whether this device belong to the user
	 *
	 * @param int $device_id        	
	 */
	public function device_get($device_id)
	{
		$user_id = $this->_check_apikey();
		
		$data = $this->device_model->get($device_id);
		if ($data === FALSE)
		{
			$this->response(array (
					'info' => "Device `$device_id` not found!"
			), 400);
		}
		else if ($user_id == $data['user_id'])
		{
			$this->response($data, 200);
		}
		else
		{
			$this->response(array (
					'info' => "Device `$device_id` out of your permission"
			), 400);
		}
	}

	/**
	 * update device info
	 *
	 * @param int $device_id        	
	 */
	public function device_put($device_id)
	{
		$this->_check_device($device_id);
		
		if ($this->put('name') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `name` required'
			), 400);
		}
		
		$input = array (
				'name' => $this->put('name'),
				'tags' => ($this->put('tags') == FALSE) ? NULL : $this->put('tags'),
				'about' => ($this->put('about') == FALSE) ? NULL : $this->put('about'),
				'locate' => ($this->put('locate') == FALSE) ? NULL : $this->put('locate')
		);
		$result = $this->device_model->update($device_id, $input);
		if ($result === TRUE)
		{
			$this->response(array (
					'info' => "Update device `$device_id` info success"
			), 200);
		}
		else
		{
			$this->response(array (
					'info' => "Update device `$device_id` info fail"
			), 400);
		}
	}

	/**
	 * delete a device by method DELETE
	 *
	 * @param int $device_id        	
	 */
	public function device_delete($device_id)
	{
		$this->_check_device($device_id);
		
		$info = $this->device_model->get($device_id);
		if ($info === FALSE)
		{
			$this->response(array (
					'info' => "Device `$device_id` not found"
			), 400);
		}
		
		$result = $this->device_model->delete($device_id);
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => "Delete device `$device_id` fail"
			), 400);
		}
		else
		{
			$this->response(array (
					'info' => "Delete device `$device_id` success"
			), 200);
		}
	}

	/**
	 * create a new sensor
	 */
	public function sensors_post()
	{
		if ($this->post('name') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `name` required'
			), 400);
		}
		if ($this->post('type') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `type` required'
			), 400);
		}
		if ($this->post('device_id') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `device_id` required'
			), 400);
		}
		
		$device_id = $this->post('device_id');
		$this->_check_device($device_id);
		
		$input = array (
				'id' => NULL,
				'name' => $this->post('name'),
				'type' => $this->post('type'),
				'tags' => ($this->post('tags') == FALSE) ? NULL : $this->post('tags'),
				'about' => ($this->post('about') == FALSE) ? NULL : $this->post('about'),
				'device_id' => $this->post('device_id'),
				'last_update' => NULL,
				'last_data' => NULL,
				'status' => 1
		);
		$result = $this->sensor_model->create($input);
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => 'Create sensor fail'
			), 400);
		}
		else
		{
			$data = array (
					'info' => "Create sensor `$result` success",
					'sensor_id' => $result
			);
			$this->response($data, 200);
		}
	}

	/**
	 * get all sensors under the specific device
	 *
	 * @param int $device_id        	
	 */
	public function sensors_get($device_id)
	{
		$data = $this->sensor_model->get_sensors($device_id);
		if ($data === FALSE)
			$this->response(array (
					'info' => "No sensors found under device `$device_id`"
			), 400);
		else
			$this->response($data, 200);
	}

	/**
	 * get info of a specific sensor
	 * check whether this sensor belong to the user
	 *
	 * @param int $sensor_id        	
	 */
	public function sensor_get($sensor_id)
	{
		$data = $this->_check_sensor($sensor_id);
		
		$this->response($data, 200);
	}

	/**
	 * update sensor info
	 *
	 * @param int $sensor_id        	
	 */
	public function sensor_put($sensor_id)
	{
		if ($this->put('name') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `name` required'
			), 400);
		}
		if ($this->put('type') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `type` required'
			), 400);
		}
		
		$device_id = $this->put('device_id');
		$this->_check_sensor($sensor_id, $device_id);
		
		$input = array (
				'name' => $this->put('name'),
				'type' => $this->put('type'),
				'tags' => ($this->put('tags') == FALSE) ? NULL : $this->put('tags'),
				'about' => ($this->put('about') == FALSE) ? NULL : $this->put('about')
		);
		
		$result = $this->sensor_model->update($sensor_id, $input);
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => "Sensor `$sensor_id` update fail"
			), 400);
		}
		else
		{
			$this->response(array (
					'info' => "Sensor `$sensor_id` update success"
			), 200);
		}
	}

	/**
	 * logically delete a sensor by change status to '0'
	 *
	 * @param int $sensor_id        	
	 */
	public function sensor_delete($sensor_id)
	{
		$this->_check_sensor($sensor_id);
		
		$result = $this->sensor_model->delete($sensor_id);
		if ($result == FALSE)
			$this->response(array (
					'info' => "Delete sensor `$sensor_id` fail"
			), 400);
		else
			$this->response(array (
					'info' => "Delete sensor `$sensor_id` success"
			), 200);
	}

	/**
	 * get user information
	 */
	public function user_get()
	{
		$apikey = $this->input->get_request_header('Apikey');
		if ($apikey === FALSE)
		{
			$this->response(array (
					'info' => 'header `apikey` lost, please login agian'
			), 400);
		}
		
		$result = $this->user_model->get_info_by_apikey($apikey);
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => '`apikey` not match, please login agian'
			), 400);
		}
		
		$this->response($result, 200);
	}

	/**
	 * update user info
	 * (not ready)
	 */
	public function user_post()
	{
		$username = $this->post('username');
		$password = $this->post('password');
		
		$data = array (
				'info' => 'Login Success',
				'username' => $username,
				'password' => $password
		);
		$this->response($data, 200);
	}

	/**
	 * user login interface
	 * return apikey and userid
	 */
	public function login_post()
	{
		$username = $this->post('username');
		$password = $this->post('password');
		$result = $this->user_model->check_pwd_by_name($username, $password);
		
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => 'Login Fail: password or username incorrect'
			), 400);
		}
		
		$data = array (
				'info' => 'Login Success',
				'id' => $result['id'],
				'username' => $result['username'],
				'apikey' => $result['apikey']
		);
		$this->response($data, 200);
	}

	/**
	 * check apikey in the HTTP header, and get user_id
	 *
	 * @return int user_id
	 */
	function _check_apikey()
	{
		$apikey = $this->input->get_request_header('Apikey');
		if ($apikey === FALSE)
		{
			$this->response(array (
					'info' => 'http header `apikey` lost, please login agian'
			), 400);
		}
		
		$result = $this->user_model->get_info_by_apikey($apikey);
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => '`apikey` not match, please login agian'
			), 400);
		}
		
		return $result['id'];
	}

	/**
	 * check if device belongs to the specific user
	 *
	 * @param int $device_id        	
	 * @param int $user_id        	
	 * @return boolean TRUE | exit
	 */
	function _check_device($device_id)
	{
		$user_id = $this->_check_apikey();
		
		$result = $this->device_model->get($device_id);
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => "device `$device_id` not found"
			), 400);
		}
		
		if ($result['user_id'] != $user_id)
		{
			$this->response(array (
					'info' => "device `$device_id` out of your permission"
			), 400);
		}
		
		return TRUE;
	}

	/**
	 * check if sensor belong to the specific user
	 * or check if this sensors belong to the specific device
	 *
	 * @param int $sensor_id        	
	 * @return boolean array | exit
	 */
	function _check_sensor($sensor_id, $device_id = FALSE)
	{
		$result = $this->sensor_model->get($sensor_id);
		if ($result === FALSE)
		{
			$this->response(array (
					'info' => "sensor `$sensor_id` not found!"
			), 400);
		}
		
		if ($device_id !== FALSE && $device_id != $result['device_id'])
		{
			$this->response(array (
					'info' => "sensor `$sensor_id` do not belong to device `$device_id`!"
			), 400);
		}
		
		$this->_check_device($result['device_id']);
		
		return $result;
	}

	/**
	 * create a datapoint for a sensor
	 * param to post:
	 * @param int sensor_id
	 * @param string value
	 */
	public function datapoint_post()
	{
		//check form field
		if ($this->post('sensor_id') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `sensor_id` required'
			), 400);
		}
		
		//check user's permission and get sensor info
		$sensor = $this->_check_sensor($this->post('sensor_id'));
		
		//check whether value is null
		if ($this->post('value') === FALSE or $this->post('value') === '')
		{
			$this->response(array (
					'info' => 'Field `value` required'
			), 400);
		}
		
		$input = array (
				'id' => NULL,
				'sensor_id' => $this->post('sensor_id'),
				'timestamp' => time(),
				'value' => $this->post('value')
		);
		$sql = $this->db->insert_string('tb_datapoint_lite', $input);
		$result = $this->db->query($sql);
		if ($result == FALSE)
		{
			$this->response(array (
					'info' => 'create datapoint fail'
			), 400);
		}
		else 
		{
			$datapoint_id = $this->db->insert_id();
			$update = array(
					'last_update' => time(),
					'last_data' => $this->post('value')
			);
			$where = array(
					'id' => $this->post('sensor_id')
			);
			$sql = $this->db->update_string('tb_sensor', $update, $where);
			$this->db->query($sql);
			$this->response(array (
					'info' => 'create datapoint success',
					'datapoint_id' => $datapoint_id
			), 200);
		}
	}
	
	/**
	 * get the lastest datapoint of a sensor
	 * @param int $sensor_id
	 */
	public function datapoint_get($sensor_id)
	{
		// check sensor first
		$sensor = $this->_check_sensor($sensor_id);

		$data = array (
				'sensor_id' => $sensor_id,
				'timestamp' => $sensor['last_update'],
				'value' => $sensor['last_data']
		);
		$this->response($data, 200);
	}
	
	/**
	 * create datapoints for duplicate sensors
	 * data should be organized as a json object array
	 * @param string json
	 */
	public function datapoints_post($device_id = FALSE)
	{
		if ($this->post('json') == FALSE)
		{
			$this->response(array (
					'info' => 'Field `json` required'
			), 400);
		}
		
		if($device_id != FALSE)
		{
			$time = time();
			$sql = "UPDATE tb_device SET last_active='$time' WHERE id='$device_id'";
			$this->db->query($sql);
		}
		
		//general type sensor value has json string, so we need these procedure
		$json_array = json_decode($this->post('json'), 'array');
		$insert_data = array();
		$update_data = array();
		foreach($json_array as $row)
		{//we should check every sensor_id in the further, validation and unique
			$insert_data[] = array(
					'sensor_id' => $row['sensor_id'],
					'timestamp' => time(),
					'value' => is_array($row['value'])?json_encode($row['value']):$row['value']
			);
			$update_data[] = array(
					'id' => $row['sensor_id'],
					'last_data' => is_array($row['value'])?json_encode($row['value']):$row['value'],
					'last_update' => time()
			);
		}
		
		$this->db->update_batch('tb_sensor', $update_data, 'id');
		$this->db->insert_batch('tb_datapoint_lite', $insert_data);
		
		$this->response(array (
				'info' => 'Datapoint created success'
		), 200);
		
	} 

	public function datapoints_get($type, $id)
	{
		//get data of all sensors under a specific device
		if(strtoupper($type) == 'DEVICE')
		{
			$this->_check_device($id);
			
			$sql = "SELECT id,last_update,last_data FROM tb_sensor WHERE device_id = $id AND `status` = 1";
			$result = $this->db->query($sql);
			$data = $result->result_array();
			$this->response($data, 200);
		}
		//get history data of a specific sensor
		else if(strtoupper($type) == 'SENSOR')
		{
			$this->_check_sensor($id);
			
			$end = time();//now
			$start = $end - 60*60;//an hour
			$interval = 60;//60 seconds, greater than 10
			//check history params
			if($this->get('start') != FALSE)
			{
				$start = $this->get('start');
			}
			if($this->get('end') != FALSE)
			{
				$start = $this->get('end');
			}
			if($this->get('interval') != FALSE)
			{
				$start = $this->get('interval');
			}
			
			$sql = "SELECT * FROM tb_datapoint_lite WHERE `sensor_id`=$id AND `timestamp` BETWEEN $start AND $end AND (`timestamp`-$start)%$interval<=30 ORDER BY `timestamp` ASC";
			$result = $this->db->query($sql);
			$data = $result->result_array();
			/* $data['start'] = $start;
			$data['end'] = $end;
			$data['interval'] = $interval;
			$data['sql'] = $sql; */
			$this->response($data, 200);
			
		}
		//lost parameter issue
		else
		{
			$this->response(array (
					'info' => 'Lost parameter `$type`'
			), 400);
		}
	}
}