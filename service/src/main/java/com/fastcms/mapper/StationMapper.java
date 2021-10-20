package com.fastcms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fastcms.entity.Station;
import com.fastcms.service.IStationService;

import java.util.List;

/**
 * <p>
 * 用户岗位表 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-07-24
 */
public interface StationMapper extends BaseMapper<Station> {

	void saveUserStation(Long userId, List<Long> stationIds);

	void deleteUserStation(Long userId);

	List<IStationService.StationVo> getUserStationList(Long userId);

}
