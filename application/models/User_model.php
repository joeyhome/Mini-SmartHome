<?php

class User_model extends CI_Model
{

	public function __construct()
	{
		$this->load->database();
	}

	public function get_info_by_name($username)
	{
		$sql = "SELECT * FROM `tb_user` WHERE `username`='$username'";
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

	public function get_info_by_id($user_id)
	{
		$sql = "SELECT * FROM `tb_user` WHERE `id`='$user_id'";
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
	
	public function get_info_by_apikey($apikey)
	{
		$sql = "SELECT * FROM `tb_user` WHERE `apikey`='$apikey'";
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

	public function check_pwd_by_name($username, $password)
	{
		$result = $this->get_info_by_name($username);
		if ($result === FALSE)
			return FALSE;
		else if ($result['password'] == md5(trim($password)))
			return $result;
		else
			return FALSE;
	}

	public function check_pwd_by_id($user_id, $password)
	{
		$result = $this->get_info_by_id($user_id);
		if ($result === FALSE)
			return FALSE;
		else if ($result['password'] == md5(trim($password)))
			return $result['id'];
		else
			return FALSE;
	}

	public function generate_apikey($user_id)
	{
		$result = $this->get_info_by_id($user_id);
		if ($result === FALSE)
		{
			return FALSE;
		}
		else
		{
			$apikey = md5($user_id . time());
			$data = array (
					'apikey' => $apikey
			);
			$where = "`id`='$user_id'";
			$sql = $this->db->update_string('tb_user', $data, $where);
			$result = $this->db->query($sql);
			if ($result == TRUE)
				return $apikey;
			else
				return FALSE;
		}
	}

	public function get_token($user_id)
	{
		$sql = "SELECT * FROM `tb_user_token` WHERE `user_id`='$user_id'";
		$result = $this->db->query($sql);
		if ($result->num_rows()> 0)
		{
			$row = $result->first_row('array');
			$token = $row['token'];
			return $token;
		}
		else
		{
			return FALSE;
		}
	}
	
	public function get_user_id($token)
	{
		$sql = "SELECT * FROM `tb_user_token` WHERE `token`='$token'";
		$result = $this->db->query($sql);
		if ($result->num_rows() > 0)
		{
			$row = $result->first_row('array');
			$user_id = $row['user_id'];
			return $user_id;
		}
		else
		{
			return FALSE;
		}
	}

	public function generate_token($user_id)
	{
		$data = array (
				'token' => sha1($user_id . time()),
				'deadline' => time() + 30 * 60 * 1000
		);
		if ($this->get_token($user_id) === FALSE)
		{
			$data['user_id'] = $user_id;
			$sql = $this->db->insert_string('tb_user_token', $data);
			$result = $this->db->query($sql);
			if ($result === TRUE)
				return $data['token'];
			else
				return FALSE;
		}
		else
		{
			$where = "`user_id`='$user_id'";
			$sql = $this->db->update_string('tb_user_token', $data, $where);
			$result = $this->db->query($sql);
			if ($result === TRUE)
				return $data['token'];
			else
				return FALSE;
		}
	}

	public function check_token($user_id, $token)
	{
		$result = $this->get_token($user_id);
		if ($result === FALSE)
		{
			return FALSE;
		}
		else if ($result == $token)
		{
			return TRUE;
		}
		else
		{
			return FALSE;
		}
	}
}