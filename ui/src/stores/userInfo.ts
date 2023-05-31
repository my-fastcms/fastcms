import { defineStore } from 'pinia';
import { Session, Local } from '/@/utils/storage';

/**
 * 用户信息
 * @methods setUserInfos 设置用户信息
 */
export const useUserInfo = defineStore('userInfo', {
	state: (): UserInfosState => ({
		userInfos: {
			username: '',
			photo: '',
			time: 0,
			roles: [],
			authBtnList: [],
		},
	}),
	actions: {
		async setUserInfos() {
			// 存储用户信息到浏览器缓存
			if (Session.get('userInfo')) {
				this.userInfos = Session.get('userInfo');
			} else if(Local.get('userInfo')) {
				this.userInfos = Local.get('userInfo');
			} else {
				const userInfos = <UserInfos>await this.getApiUserInfo();
				this.userInfos = userInfos;
			}
		},

		async getApiUserInfo() {
			return new Promise((resolve) => {
				setTimeout(() => {
					let userInfos = Session.get('userInfo');
					if(!userInfos) {
						userInfos = Local.get('userInfo');
					}
					resolve(userInfos);
				}, 0);
			});
		},
	},
});
