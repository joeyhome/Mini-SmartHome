<?php

/**
 * Enter description here ...
 * @author 沐浴新光
 *
 */
class Device_model extends CI_Model
{

	public function __construct()
	{
		$this->load->database();
	}

	/**
	 * create a device
	 * $data is an array organized by controller
	 *
	 * @return boolean FALSE or last insert ID
	 */
	public function create($data)
	{
		$sql = $this->db->insert_string('tb_device', $data);
		$result = $this->db->query($sql);
		if ($result === TRUE)
		{
			return $this->db->insert_id();
		}
		else
		{
			return FALSE;
		}
	}

	/**
	 * get info of a specific device
	 * if `status`=0, we treat it as deleted, and don't deal with it
	 *
	 * @param int $device_id        	
	 * @return boolean FALSE | array
	 */
	public function get($device_id)
	{
		$sql = "SELECT * FROM `tb_device` WHERE `id`='$device_id' AND  `status`=1";
		$result = $this->db->query($sql);
		if ($result->num_rows()> 0)
		{
			return $result->first_row('array');
		}
		else
		{
			return FALSE;
		}
	}

	public function update($device_id, $data)
	{
		$where = "`id`='$device_id'";
		$sql = $this->db->update_string('tb_device', $data, $where);
		$result = $this->db->query($sql);
		if ($this->db->affected_rows() > 0)
			return TRUE;
		else 
			return FALSE;
	}

	/**
	 * we provide user a chance to regret, so we don't truely delete a device
	 * when this method is called by users
	 * we may physical delete after some time if you don't recover it
	 * the amount of time is not determined yet
	 *
	 * @param int $device_id        	
	 * @return boolean
	 */
	public function delete($device_id)
	{
		if ($device_id == FALSE)
			return FALSE;
		
		$where = "`id`='$device_id'";
		$data = array (
				'status' => 0
		);
		$sql = $this->db->update_string('tb_device', $data, $where);
		$this->db->query($sql);
		if ($this->db->affected_rows() > 0)
			return TRUE;
		else
			return FALSE;
	}

	/**
	 * get all devices owned by the user
	 *
	 * @param int $user_id        	
	 * @return boolean FALSE | array
	 */
	public function get_devices($user_id)
	{
		$sql = "SELECT * FROM `tb_device` WHERE `user_id`='$user_id' AND `status`=1";
		$result = $this->db->query($sql);
		if ($result->num_rows() > 0)
		{
			return $result->result_array();
		}
		else
		{
			return FALSE;
		}
	}
}