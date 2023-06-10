<!-- <template>
	<div class="login-scan-container">
		<div ref="qrcodeRef"></div>
		<div class="font12 mt20 login-msg">
			<i class="iconfont icon-saoyisao mr5"></i>
			<span>{{ $t('message.scan.text') }}</span>
		</div>
	</div>
</template> -->

<template>
	<div class="login-scan-container">
		<el-image class="login-scan-qrcode" ref="qrcodeRef" :src="state.qrcodeUrl"></el-image>
	</div>
</template>

<script setup lang="ts" name="loginScan">
import { ref, onMounted, reactive, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { useI18n } from 'vue-i18n';
// import Cookies from 'js-cookie';
import { initFrontEndControlRoutes } from '/@/router/frontEnd';
import { initBackEndControlRoutes } from '/@/router/backEnd';
import { Local, Session } from '/@/utils/storage';
import { formatAxis } from '/@/utils/formatTime';
import { LoginApi } from '/@/api/login/index';
// import QRCode from 'qrcodejs2-fixes';
import { dynamicRoutes } from '/@/router/route';

const { t } = useI18n();
const route = useRoute();
const router = useRouter();
const loginApi = LoginApi();
// 定义变量内容
const qrcodeRef = ref<HTMLElement | null>(null);

const state = reactive({
	sceneId: "",
	qrcodeUrl: "",
});

// 时间获取
const currentTime = computed(() => {
	return formatAxis(new Date());
});


// 初始化生成二维码
// const initQrcode = () => {
// 	nextTick(() => {
// 		(<HTMLElement>qrcodeRef.value).innerHTML = '';
// 		new QRCode(qrcodeRef.value, {
// 			text: `https://qm.qq.com/cgi-bin/qm/qr?k=RdUY97Vx0T0vZ_1OOu-X1yFNkWgDwbjC&jump_from=webapi`,
// 			width: 260,
// 			height: 260,
// 			colorDark: '#000000',
// 			colorLight: '#ffffff',
// 		});
// 	});
// };

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
		photo: res.data.headImg === null || res.data.headImg == '' ? '/header.jpg' : res.data.headImg,
		time: new Date().getTime(),
		hasRole: res.data.hasRole,
		version: res.data.version,
		userType: res.data.userType,
	};
	Session.set('token', res.data.token);
	Session.set('tokenTtl', res.data.tokenTtl);
	// 存储 token 到浏览器缓存
	Local.set('token', res.data.token);
	Local.set('tokenTtl', res.data.tokenTtl);
	// 存储用户信息到浏览器缓存
	Local.set('userInfo', userInfos);
	Session.set('userInfo', userInfos);
	// 1、请注意执行顺序(存储用户信息到vuex)
	// store.dispatch('userInfos/setUserInfos', userInfos);

	if(res.data.userType === 1) {
		// 后端管理用户初始化后端动态路由
		await initBackEndControlRoutes();					
	} else if(res.data.userType === 2) {
		// 前台用户初始化前端静态路由
		await initFrontEndControlRoutes();
	}

	// 初始化登录成功时间问候语
	let currentTimeInfo = currentTime.value;
	// 登录成功，跳到转首页
	// 添加完动态路由，再进行 router 跳转，否则可能报错 No match found for location with path "/"
	// 如果是复制粘贴的路径，非首页/登录页，那么登录成功后重定向到对应的路径中
	if (route.query?.redirect) {
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
		const signInText = t('message.signInText');
		ElMessage.success(`${currentTimeInfo}，${signInText}`);
	}, 300);
}

// 页面加载时
onMounted(() => {
	// initQrcode();
	loginApi.getLoginQrcode().then(res => {
		state.qrcodeUrl = res.data.qrcodeUrl || null;
		state.sceneId = res.data.sceneId || null;
		// initQrcode();

		let timer = setInterval(() => {
			loginApi.getLoginUser(state.sceneId).then((res) => {
				clearInterval(timer)
				doLogin(res)
			}).catch(error => {
				console.log(error)
			})
		}, 1000);

	})
});
</script>

<style scoped lang="scss">
.login-scan-animation {
	opacity: 0;
	animation-name: error-num;
	animation-duration: 0.5s;
	animation-fill-mode: forwards;
}
.login-scan-container {
	padding: 0 20px 20px;
	display: flex;
	flex-direction: column;
	text-align: center;
	@extend .login-scan-animation;
	animation-delay: 0.1s;
	:deep(img) {
		margin: auto;
	}
	.login-msg {
		display: flex;
		align-items: center;
		justify-content: center;
		color: var(--el-text-color-placeholder);
		@extend .login-scan-animation;
		animation-delay: 0.2s;
	}
}
</style>
