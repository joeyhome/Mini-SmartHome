<?php
/*
description of table `tb_sensor`
id 				int
name 			varchar(20)
type 			tinyint
tags 			varchar(50)
about 			varchar(100)
device_id		int
create_time 	int
last_update 	int
last_data		varchar(100)
status 			tinyint
*/
class Sensor_model extends CI_Model
{

	public function __construct()
	{
		$this->load->database();
	}

	/**
	 * create a sensor
	 * $data is an array organized by controller
	 * @return boolean FALSE or last insert ID
	 */
	public function create($data)
	{
		$sql = $this->db->insert_string('tb_sensor', $data);
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

	public function get($sensor_id)
	{
		$sql = "SELECT * FROM `tb_sensor` WHERE `id`='$sensor_id' AND `status`='1'";
		$result = $this->db->query($sql);
		if ($result->num_rows() > 0)
		{
			return $result->first_row('array');
		}
		else
		{
			return FALSE;
		}
	}

	public function update($sensor_id, $data)
	{
		$where = "`id`='$sensor_id'";
		$sql = $this->db->update_string('tb_sensor', $data, $where);
		$result = $this->db->query($sql);
		if ($this->db->affected_rows() > 0)
			return TRUE;
		else 
			return FALSE;
	}

	public function delete($sensor_id)
	{
		if($sensor_id == FALSE)
			return FALSE;
		
		$where = "`id`='$sensor_id'";
		$data = array(
			'last_update' => time(),
			'status' => 0
		);
		$sql = $this->db->update_string('tb_sensor', $data, $where);
		$this->db->query($sql);
		if($this->db->affected_rows() > 0)
			return TRUE;
		else
			return FALSE;
	}

	public function get_sensors($device_id)
	{
		$sql = "SELECT * FROM `tb_sensor` WHERE `device_id`='$device_id' AND `status`=1";
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