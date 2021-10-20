package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Station;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 用户岗位表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-07-24
 */
public interface IStationService extends IService<Station> {

	void saveUserStation(Long userId, List<Long> stationIds);

	void deleteUserStation(Long userId);

	List<StationVo> getUserStationList(Long userId);

	@Data
	class StationVo extends Station {
		Long userId;
	}

}
