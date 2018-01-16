package com.dbumama.market.web.admin.product.controller;

import com.dbumama.market.model.Specification;
import com.dbumama.market.service.api.specification.SpecificationResultDto;
import com.dbumama.market.service.api.specification.SpecificationService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;

@RouteBind(path = "specification", viewPath = "specification")
public class SpecificationController extends AdminBaseController<Specification> {
	@BY_NAME
	private SpecificationService specificationService;

	public void index() {
		render("specification_index.html");
	}

	public void list() {
		rendSuccessJson(specificationService.list(getSellerId(), getPageNo(), getPageSize()));
	}

	public void add() {
		render("specification_add.html");
	}

	public void edit() {
		Specification specification = (Specification) specificationService.findById(getParaToLong(0));
		if (specification != null) {
			setAttr("specification", specification);
			setAttr("specifitionValue", specificationService.getSpeciValues(specification.getId()));
		}
		render("specification_add.html");
	}

	public void save() {
		Specification specification = null;
		try {
			specification = getModel();
			Specification specificationDto = specificationService.save(specification, getPara("items"), getSellerId());
			rendSuccessJson(specificationDto);
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson(e.getMessage());
		}
	}

	public void addSpecification() {
		render("specification_new_add.html");
	}

	public void saveSpecification() {
		Specification specification = null;
		try {
			specification = getModel();
			Specification specificationDto = specificationService.save(specification, getPara("items"), getSellerId());
			SpecificationResultDto dto = specificationService.getSpeciAndVaules(specificationDto.getId());
			rendSuccessJson(dto);
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}

	@Override
	protected Class<Specification> getModelClass() {
		return Specification.class;
	}

}
