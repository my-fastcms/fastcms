package com.dbumama.market.service.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.kit.StrKit;

/**
 * jfinal sql动态查询通用类
 * @author wangjun
 *
 */
@SuppressWarnings("serial")
public class QueryHelper implements Serializable{
	private String select;
	private String sqlExceptSelect;
	private List<Object> params = new ArrayList<Object>();
	private StringBuilder whereBuilder = new StringBuilder(" where 1=1 ");
	private StringBuilder orderBy = new StringBuilder(" order by ");
	
	private List<Where> wheres;
	
	public QueryHelper(){}
	
	public QueryHelper(String select, String sqlExceptSelect){
		this.select = select;
		this.sqlExceptSelect = sqlExceptSelect;
	}
	
	public QueryHelper addWhere(String field, Object value, int type){
		if(StrKit.isBlank(field) || value == null) return this;
        if(wheres == null)
			wheres = new ArrayList<Where>();
		Where where = new Where (field, value, type);
		wheres.add(where);
        return this;
    }
	
	public String getSelect() {
		return select;
	}

	public String getSqlExceptSelect() {
		return sqlExceptSelect + getWhere();
	}
	
	/**
	 * 等于
	 * @param field
	 * @param value
	 * @return
	 */
	public QueryHelper addWhere(String field, Object value){
		return addWhere(field, value, Type.EQUAL);
	}
	
	/**
	 * 不等于
	 * @param field
	 * @param value
	 * @return
	 */
	public QueryHelper addWhereNOT_EQUAL(String field, Object value){
		return addWhere(field, value, Type.NOT_EQUAL);
	}
	
	/**
	 * 大于
	 * @param field
	 * @param value
	 * @return
	 */
	public QueryHelper addWhereTHEN_G(String field, Object value){
		return addWhere(field, value, Type.THEN_G);
	}
	
	/**
	 * 大于等于
	 * @param field
	 * @param value
	 * @return
	 */
	public QueryHelper addWhereTHEN_GE(String field, Object value){
		return addWhere(field, value, Type.THEN_GE);
	}
	
	/**
	 * 小于
	 * @param field
	 * @param value
	 * @return
	 */
	public QueryHelper addWhereTHEN_L(String field, Object value){
		return addWhere(field, value, Type.THEN_L);
	}
	
	/**
	 * 小于等于
	 * @param field
	 * @param value
	 * @return
	 */
	public QueryHelper addWhereTHEN_LE(String field, Object value){
		return addWhere(field, value, Type.THEN_LE);
	}
	
	/**
	 * between
	 * @param field
	 * @param value
	 * @return
	 */
	public QueryHelper addWhereBETWEEN(String field, Object value){
		return addWhere(field, value, Type.BETWEEN);
	}
	
	public QueryHelper addWhereIn(String field, Object value){
		return addWhere(field, value, Type.IN);
	}
	
	public QueryHelper addWhereNotIn(String field, Object value){
		return addWhere(field, value, Type.NOT_IN);
	}
	
	public QueryHelper addWhereLike(String field, Object value){
		return addWhere(field, value, Type.LIKE);
	}
	
	public QueryHelper addWhereLikeLeft(String field, Object value){
		return addWhere(field, value, Type.LIKE_LEFT);
	}
	
	public QueryHelper addWhereLikeRight(String field, Object value){
		return addWhere(field, value, Type.LIKE_RIGHT);
	}
	
	public QueryHelper addOrderBy(String type, String ... fields){
		if(StrKit.isBlank(type) || fields == null) return this;
		for(String f : fields){
			orderBy.append(f).append(",");
		}
		orderBy = orderBy.deleteCharAt(orderBy.length()-1).append(" ").append(type);
		return this;
	}
	
	public QueryHelper build(){
		for(Where where : wheres){
			buildSQL(where);
		}
		return this;
	}
	
	public String getWhere(){
		return (getOrderBy().contains("desc") || getOrderBy().contains("asc")) ? whereBuilder.append(getOrderBy()).toString() : whereBuilder.toString();
	}
	
	public Object [] getParams(){
		return params.toArray();
	}
	
	public String getOrderBy(){
		return orderBy.toString();
	}
	
    void buildSQL(Where where) {
        Object value = where.getValue();
        switch (where.getType()) {
        case Type.LIKE:
        	whereBuilder.append(" and " + where.getField() + " like ? ");
			params.add("%"+value+"%");
            break;
        case Type.LIKE_LEFT:
        	whereBuilder.append(" and " + where.getField() + " like ? ");
			params.add("%"+value);
            break;
        case Type.LIKE_RIGHT:
        	whereBuilder.append(" and " + where.getField() + " like ? ");
			params.add(value+"%");
            break;
        case Type.EQUAL:
        	whereBuilder.append(" and " + where.getField() + " = ? ");
			params.add(value);
        	break;
        case Type.THEN_G:
        	whereBuilder.append(" and " + where.getField() + " > ? ");
			params.add(value);
            break;
        case Type.THEN_GE:
        	whereBuilder.append(" and " + where.getField() + " >= ? ");
			params.add(value);
            break;
        case Type.THEN_L:
        	whereBuilder.append(" and " + where.getField() + " < ? ");
			params.add(value);
            break;
        case Type.THEN_LE:
        	whereBuilder.append(" and " + where.getField() + " <= ? ");
			params.add(value);
            break;
        case Type.NOT_EQUAL:
        	whereBuilder.append(" and " + where.getField() + " != ? ");
			params.add(value);
            break;
        case Type.IS_NULL:
        	whereBuilder.append(" and " + where.getField() + " is null ");
            break;
        case Type.IS_NOT_NULL:
        	whereBuilder.append(" and " + where.getField() + " is not null ");
        	break;
        case Type.BETWEEN :
        	if(value instanceof List){
        		@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) value;
        		if(list!= null && list.size()==2){
        			whereBuilder.append(" and " + where.getField() + " between ? and ? ");
        			for(Object obj : list){
        				params.add(obj);
        			}
        		}
        	}
        	break;
        case Type.IN:
        	if(value instanceof List){
        		@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) value;
        		if(list != null && list.size()>0){
        			StringBuffer instr = new StringBuffer();
                	whereBuilder.append(" and " + where.getField() + " in (");
                	for (Object obj : list) {
                	    instr.append("?").append(",");
                	    params.add(obj);
                	}
                	instr.deleteCharAt(instr.length() -1);
                	whereBuilder.append(instr + ") ");        			
        		}
        	}
            break;
        case Type.NOT_IN:
        	if(value instanceof List){
        		@SuppressWarnings("unchecked")
				List<Object> list = (List<Object>) value;
        		if(list != null && list.size()>0){
        			StringBuffer instr = new StringBuffer();
                	whereBuilder.append(" and " + where.getField() + " not in (");
                	for (Object obj : list) {
                	    instr.append("?").append(",");
                	    params.add(obj);
                	}
                	instr.deleteCharAt(instr.length() -1);
                	whereBuilder.append(instr + ") ");        			
        		}
        	}
        	break;
        }
    }
	
}
