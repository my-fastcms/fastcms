package com.dbumama.market.service.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.Specification;
import com.dbumama.market.model.SpecificationValue;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.api.specification.SpecificationException;
import com.dbumama.market.service.api.specification.SpecificationParamDto;
import com.dbumama.market.service.api.specification.SpecificationResultDto;
import com.dbumama.market.service.api.specification.SpecificationService;
import com.dbumama.market.service.api.specification.SpecificationsResultDto;
import com.dbumama.market.service.base.BaseServiceImpl;
import com.dbumama.market.service.sql.QueryHelper;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

@Service("specificationService")
public class SpecificationServiceImpl extends BaseServiceImpl<Specification> implements SpecificationService {

	static final Specification specificationDao = new Specification().dao();
	private static final SpecificationValue specValueDao = new SpecificationValue().dao();
	private static final Specification specDao = new Specification().dao();

	@Override
	public List<SpecificationResultDto> findAll(SpecificationParamDto specificationParamDto) {

		if (specificationParamDto == null || specificationParamDto.getSellerId() == null)
			throw new MarketBaseException("参数错误");

		List<SpecificationResultDto> results = new ArrayList<SpecificationResultDto>();
		List<Specification> specifications = specDao.find("SELECT * FROM "+Specification.table+" where seller_id IS NULL or seller_id=?", specificationParamDto.getSellerId());
		for (Specification specifcation : specifications) {
			SpecificationResultDto sdto = new SpecificationResultDto();
			sdto.setSpecification(specifcation);
			List<SpecificationValue> specificationValues = specValueDao.find("SELECT * FROM "+SpecificationValue.table+" WHERE `specification_id`=? and active=1 ", specifcation.getId());
			sdto.setSpecificationValues(specificationValues);
			results.add(sdto);
		}
		return results;
	}

	@Override
	@Transactional(rollbackFor = SpecificationException.class)
	public Specification save(Specification specification, String items, Long sellerId) {
		if (StrKit.isBlank(items) || sellerId == null || specification == null)
			throw new SpecificationException("规格值参数异常出错");
		try {
			specification.setSellerId(sellerId);
			specification.set("active", 1);
			specification.set("type", 1);
			
			if(specification.getId() == null)
				specification.save();
			else
				specification.update();
			
		} catch (Exception e) {
			throw new SpecificationException("系统异常，保存出错");
		}
		
		JSONArray jsonArray = JSONArray.parseArray(items);
		if (jsonArray == null || jsonArray.size() <= 0)
			throw new SpecificationException("请填写规格值");

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			if(StrKit.isBlank(json.getString("name")) || json.getInteger("orders")==null) continue;
			
			if (json.getLong("itemId") != null) {
				SpecificationValue values = specValueDao.findById(json.getLong("itemId"));
				values.setSpecificationId(specification.getId());
				values.setName(json.getString("name"));
				values.setOrders(json.getInteger("orders"));
				values.setImage(null);
				values.setSellerId(sellerId);
				if("del".equals(json.get("del"))){
					values.setActive(0);
				}else{
					values.setActive(1);					
				}
				values.setUpdated(new Date());
				try {
					values.update();
				} catch (Exception e) {
					throw new SpecificationException("系统异常，保存出错" + e.getMessage());
				}
			} else {
				if("del".equals(json.get("del"))){
					continue;
				}
				
				SpecificationValue values = new SpecificationValue();
				values.setSpecificationId(specification.getId());
				values.setName(json.getString("name"));
				values.setOrders(json.getInteger("orders"));
				values.setImage(null);
				values.setSellerId(sellerId);
				values.setActive(1);
				values.setUpdated(new Date());
				values.setCreated(new Date());
				try {
					values.save();
				} catch (Exception e) {
					throw new SpecificationException("系统异常，保存出错," + e.getMessage());
				}
			}
		}
		return specification;
	}

	@Override
	public SpecificationResultDto getSpeciAndVaules(Long specId) {
		if (specId == null)
			throw new MarketBaseException("参数错误");
		Specification specification = specDao.findById(specId);
		SpecificationResultDto sdto = new SpecificationResultDto();
		sdto.setSpecification(specification);
		List<SpecificationValue> specificationValues = getSpeciValues(specification.getId());
		sdto.setSpecificationValues(specificationValues);
		return sdto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbumama.market.service.api.base.BaseService#findById(java.lang.
	 * Object)
	 */
	@Override
	public Specification findById(Object id) {
		return specificationDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbumama.market.service.base.BaseServiceImpl#getModel()
	 */
	@Override
	protected Model<Specification> getModel() {
		return specificationDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbumama.market.service.api.specification.SpecificationService#list(
	 * java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Page<SpecificationsResultDto> list(Long sellerId, Integer pageNo, Integer pageSize) {

		QueryHelper helper = new QueryHelper("select *", "from " + Specification.table);
		helper.addWhere("seller_id", sellerId).addOrderBy("desc", "updated").build();

		Page<Specification> specificationpage = specDao.paginate(pageNo, pageSize, helper.getSelect(),
				helper.getSqlExceptSelect(), helper.getParams());
		List<SpecificationsResultDto> resultDto = new ArrayList<SpecificationsResultDto>();
		for (Specification s : specificationpage.getList()) {
			SpecificationsResultDto sResultDto = new SpecificationsResultDto();
			sResultDto.setId(s.getId());
			sResultDto.setName(s.getName());
			sResultDto.setCreated(s.getCreated());
			List<SpecificationValue> specificationValues = specValueDao
					.find("select * from " + SpecificationValue.table + " where specification_id=? and active=1 ", s.getId());
			sResultDto.setSpecificationValues(specificationValues);
			resultDto.add(sResultDto);
		}
		Page<SpecificationsResultDto> sresultPage = new Page<>(resultDto, pageNo, pageSize,
				specificationpage.getTotalPage(), specificationpage.getTotalRow());

		return sresultPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbumama.market.service.api.specification.SpecificationService#
	 * getSpeciValues(java.lang.Long)
	 */
	@Override
	public List<SpecificationValue> getSpeciValues(Long specId) {
		return specValueDao.find(
				"SELECT * FROM " + SpecificationValue.table + " WHERE `specification_id` =? and active=1 order by orders asc",
				specId);
	}

}
