package org.platform.crawler.webmagic.modules.abstr.mapper;

import java.io.Serializable;
import java.util.List;

import org.platform.crawler.webmagic.modules.abstr.entity.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("genericMapper")
public interface GenericMapper<Entity extends Serializable, PK extends Serializable> {

	/**
	 * 单条记录插入
	 * @param entity
	 * @throws DataAccessException
	 */
	public void insert(Entity entity) throws DataAccessException;
	
	/**
	 * 多条批量插入
	 * @param entities
	 * @throws DataAccessException
	 */
	public void insert(List<Entity> entities) throws DataAccessException;

	/**
	 * 单条记录更新
	 * @param entity
	 * @throws DataAccessException
	 */
	public void update(Entity entity) throws DataAccessException;
	
	/**
	 * 多条批量更新
	 * @param entities
	 * @throws DataAccessException
	 */
	public void update(List<Entity> entities) throws DataAccessException;

	/**
	 * 删除单条记录
	 * @param entity
	 * @throws DataAccessException
	 */
	public void delete(Entity entity) throws DataAccessException;

	/**
	 * 根据主键删除单条记录
	 * @param pk
	 * @throws DataAccessException
	 */
	public void deleteByPK(PK pk) throws DataAccessException;
	
	/**
	 * 根据主键读取单条记录
	 * @param pk
	 * @return
	 * @throws DataAccessException
	 */
	public Entity readDataByPK(PK pk) throws DataAccessException;
	
	/**
	 * 根据条件读取单条记录
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public Entity readDataByCondition(Query query) throws DataAccessException;
	
	/**
	 * 根据条件读取多条记录
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<Entity> readDataListByCondition(Query query) throws DataAccessException;
	
	
}
