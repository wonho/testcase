package com.base.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

public class CommonDaoImpl implements CommonDao {

	protected SqlSessionTemplate template;

	public Integer queryForInt(String statementId, Object parameter) {
		return (Integer) template.selectOne(statementId, parameter);
	}

	public Long queryForLong(String statementId, Object parameter) {
		return (Long) template.selectOne(statementId, parameter);
	}

	public <T> T queryForObject(String statementId, Object parameter, Class<T> clazz) {
		return clazz.cast(template.selectOne(statementId, parameter));
	}

	public Object queryForObject(String statementId, Object parameter) {
		return template.selectOne(statementId, parameter);
	}

	public Map<?, ?> queryForMap(String statementId, String mapKey,	Object parameter) {
		return template.selectMap(statementId, parameter, mapKey);
	}

	public <T> List<T> queryForList(String statementId, Object parameter, Class<T> clazz) {
		return (List<T>) template.selectList(statementId, parameter);
	}

	public List<?> queryForList(String statementId, Object parameter) {
		return template.selectList(statementId, parameter);
	}

	public <T> List<T> queryForList(String statementId, Object parameter, int skipRows, int maxRows, Class<T> clazz) {
		return (List<T>) template.selectList(statementId, parameter, new RowBounds(skipRows, maxRows));
	}

	/*
	 * (non-Javadoc)
	 * @see net.sysbank.framework.persistence.CommonDao#queryWithResultHandler(java.lang.String, java.lang.Object, net.sysbank.framework.data.handler.StreamHandler)
	 */
//	public <T> void queryWithResultHandler(String statementId, Object parameter, final StreamHandler<T> streamHandler) {
//		streamHandler.open();
//
//		try {
//			template.select(statementId, parameter, new ResultHandler() {
//
//				@Override
//				public void handleResult(ResultContext context) {
//					streamHandler.handleRow((T) context.getResultObject());
//				}
//			});
//		} finally {
//			streamHandler.close();
//		}
//	}

	public Integer update(String statementId, Object parameter) {
		return template.update(statementId, parameter);
	}

	public Integer updateWithLogging(String selectStatementId, String updateStatementId, Object parameter){
		List<?> before = queryForList(selectStatementId, parameter);
		Integer affectedRow = template.update(updateStatementId, parameter);
		List<?> after = queryForList(selectStatementId, parameter);

		return affectedRow;
	}

	public Object insert(String statementId, Object parameter) {
		return template.insert(statementId, parameter);
	}

	public Integer delete(String statementId, Object parameter) {
		return template.delete(statementId, parameter);
	}

	public Integer deleteWithLogging(String selectStatementId, String deleteStatementId, Object parameter) {
		List<?> before = queryForList(selectStatementId, parameter);
		Integer affectedRow = template.delete(deleteStatementId, parameter);
		List<?> after = queryForList(selectStatementId, parameter);

//		if(dataLogger != null)	{
//			dataLogger.persist(before, after);
//		}

		return affectedRow;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate template) {
		this.template = template;
	}

//	public void setDataLogger(DataLogger dataLogger) {
//		this.dataLogger = dataLogger;
//	}
}

