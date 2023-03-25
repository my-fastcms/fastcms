<template>
	<div class="login-scan-container">
		<div class="login-scan-qrcode" ref="qrcodeRef"></div>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, defineComponent, onMounted, getCurrentInstance } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { dynamicRoutes } from '/@/router/route';
import { initFrontEndControlRoutes } from '/@/router/frontEnd';
import { initBackEndControlRoutes } from '/@/router/backEnd';
import { useStore } from '/@/store/index';
import { Local } from '/@/utils/storage';
import {creatWebSocket, sendWebSocket, closeWebSocket} from '/@/utils/websocket';
import { getLoginQrcode, getLoginUser } from '/@/api/login/index';
import QRCode from 'qrcodejs2-fixes';
import { stat } from 'fs';
export default defineComponent({
	name: 'login11',
	props: {
		domain: {
			type: String,
			default: () => '',
		},
	},
	setup(props) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			sceneId: null,
			qrcodeUrl: null,
		});
		// 初始化生成二维码
		const initQrcode = () => {
			proxy.$refs.qrcodeRef.innerHTML = '';
			new QRCode(proxy.$refs.qrcodeRef, {
				text: state.qrcodeUrl,
				width: 260,
				height: 260,
				colorDark: '#000000',
				colorLight: '#ffffff',
			});
		};

		
		const doLogin = async(res: any) => {

			//let defaultAuthPageList: Array<string> = ['admin'];
			//let defaultAuthBtnList: Array<string> = ['btn.add', 'btn.del', 'btn.edit', 'btn.link'];
			if(res.data.userType != 1 && res.data.userType != 2) {
				ElMessage.error("未知用户类型");
				return;
			}

			if(res.data.userType === 1 && res.data.hasRole == false) {
				ElMessage.error("请联系管理员授权");
			} else {
				signInSuccess(res);				
			}

		}

		// 登录成功后的跳转
		const signInSuccess = async(res: any) => {

			const userInfos = {
				username: res.data.username,
				photo: res.data.headImg === null ? '/header.jpg' : res.data.headImg,
				time: new Date().getTime(),
				hasRole: res.data.hasRole,
				version: res.data.version,
				userType: res.data.userType,
			};
			// 存储 token 到浏览器缓存
			Local.set('token', res.data.token);
			Local.set('tokenTtl', res.data.tokenTtl);
			// 存储用户信息到浏览器缓存
			Local.set('userInfo', userInfos);
			// 1、请注意执行顺序(存储用户信息到vuex)
			store.dispatch('userInfos/setUserInfos', userInfos);

			if (!store.state.themeConfig.themeConfig.isRequestRoutes) {
				// 前端控制路由，2、请注意执行顺序
				await initFrontEndControlRoutes();
			} else {
				if(res.data.userType === 1) {
					// 后端管理用户初始化后端动态路由
					await initBackEndControlRoutes();					
				} else if(res.data.userType === 2) {
					// 前台用户初始化前端静态路由
					await initFrontEndControlRoutes();
				}
			}

			// 初始化登录成功时间问候语
			let currentTimeInfo = currentTime.value;
			// 登录成功，跳到转首页
			// 添加完动态路由，再进行 router 跳转，否则可能报错 No match found for location with path "/"
			// 如果是复制粘贴的路径，非首页/登录页，那么登录成功后重定向到对应的路径中
			if (route.query?.redirect) {
				console.log(route.query)
				router.push({
					path: route.query?.redirect,
					query: Object.keys(route.query?.params).length > 0 ? JSON.parse(route.query?.params) : '',
				});
			} else {
				const views = dynamicRoutes[0].children;
				const gotoPath = views[0].path || '/'
				router.push(gotoPath);
			}
			// 登录成功提示
			setTimeout(() => {
				
				// 关闭 loading
				state.loading.signIn = true;
				const signInText = t('message.signInText');
				ElMessage.success(`${currentTimeInfo}，${signInText}`);
				// 修复防止退出登录再进入界面时，需要刷新样式才生效的问题，初始化布局样式等(登录的时候触发，目前方案)
				proxy.mittBus.emit('onSignInClick');
			}, 300);
		}

		// 页面加载时
		onMounted(() => {
			getLoginQrcode().then(res => {
				state.qrcodeUrl = res.data.qrcodeUrl || null;
				state.sceneId = res.data.sceneId || null;
				console.log("sceneId:" + state.sceneId);
				console.log("qrcodeUrl:" + state.qrcodeUrl)
				initQrcode();

				setInterval(() => {
					getLoginUser(state.sceneId).then((res) => {
						clearInterval()
						doLogin(res)
					}).catch(() => {
						console.log("=====no login user find=====");
					})
				}, 1000);

			})
			//creatWebSocket();
		});

		return {
			...toRefs(state),
		};
	},
});
</script>

<style scoped lang="scss">
.login-scan-container {
	.login-scan-qrcode {
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -40%);
	}
}
</style>
