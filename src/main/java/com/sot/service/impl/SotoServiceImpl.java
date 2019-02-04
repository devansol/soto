package com.sot.service.impl;

import static org.mockito.Matchers.booleanThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sot.DbConnection;
import com.sot.dao.SotoDao;
import com.sot.service.SotoService;

@Service
public class SotoServiceImpl extends DbConnection implements SotoService {
	
	@Autowired
	private SotoDao sotoDao;
	
	@Override
	public List<Map<String, Object>> getCustomer(int customer_id) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			sotoDao.setConnection(this.conn);
			list = sotoDao.getCustomer(customer_id);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getTicket(int ticket_id) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			sotoDao.setConnection(this.conn);
			list = sotoDao.getTicket(ticket_id);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}
	
	@Override
	public Map<String, Object> insert(int customer_id, int ticket_id, int buy) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean insert = false;
		int quantity = 0;
		int max = 0;
		try{
			this.conn = this.getConnection();
			sotoDao.setConnection(this.conn);
			quantity = sotoDao.getQuantity(ticket_id);
			
			quantity -= buy;
			if(quantity < 0) {
				result.put("message", "Quantity tidak tersedia untuk " + buy + " tiket !");
			}else{
				insert = sotoDao.insert(customer_id, ticket_id, buy);
				if(insert){
					if(sotoDao.updateQuantity(ticket_id, quantity)){
						max = sotoDao.getMaxOrder();
						result.put("message", true);
						result.put("order_max", max);
					}
				}else{
					result.put("message", false);
				}
			}
		}catch (Exception e) {
			if(this.conn != null){
				this.conn.rollback();
			}
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getAllCustomer() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			sotoDao.setConnection(this.conn);
			list = sotoDao.getAllCustomer();
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getAllTicket() throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			sotoDao.setConnection(this.conn);
			list = sotoDao.getAllTicket();
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getListCustomer(String value) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			sotoDao.setConnection(this.conn);
			list = sotoDao.getListCustomer(value);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();;
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getListFilm(String value) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			this.conn = this.getConnection();
			sotoDao.setConnection(this.conn);
			list = sotoDao.getListFilm(value);
		}catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally {
			this.conn.close();
		}
		return list;
	}
	

}
