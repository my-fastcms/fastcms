package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Station;
import com.fastcms.mapper.StationMapper;
import com.fastcms.service.IStationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户岗位表 服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-07-24
 */
@Service
public class StationServiceImpl extends ServiceImpl<StationMapper, Station> implements IStationService {

	@Override
	public void saveUserStation(Long userId, List<Long> stationIds) {
		deleteUserStation(userId);
		getBaseMapper().saveUserStation(userId, stationIds);
	}

	@Override
	public void deleteUserStation(Long userId) {
		getBaseMapper().deleteUserStation(userId);
	}

	@Override
	public List<StationVo> getUserStationList(Long userId) {
		return getBaseMapper().getUserStationList(userId);
	}

}
