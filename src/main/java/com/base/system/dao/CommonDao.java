package com.base.system.dao;

import java.util.List;
import java.util.Map;

public interface CommonDao {

	Integer queryForInt(String statementId, Object parameter);

	Long queryForLong(String statementId, Object parameter);

	<T> T queryForObject(String statementId, Object parameter, Class<T> clazz);

	Object queryForObject(String statementId, Object parameter);

	Map<?, ?> queryForMap(String statementId, String mapKey, Object parameter);

	<T> List<T> queryForList(String statementId, Object parameter, Class<T> clazz);

	List<?> queryForList(String statementId, Object parameter);

	<T> List<T> queryForList(String statementId, Object parameter, int skipRows, int maxRows, Class<T> clazz);

//	<T> void queryWithResultHandler(String statementId, Object parameter, StreamHandler<T> streamHandler);

	Integer update(String statementId, Object parameter);

	Integer updateWithLogging(String selectStatementId, String updateStatementId, Object parameter);

	Object insert(String statementId, Object parameter);

	Integer delete(String statementId, Object parameter);

	Integer deleteWithLogging(String selectStatementId, String deleteStatementId, Object parameter);

}

