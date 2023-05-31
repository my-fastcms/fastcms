<template>
	<el-form size="large" class="login-content-form" :model="state.myForm" :rules="state.rules" ref="myRefForm">
		<el-form-item class="login-animation1" prop="username">
			<el-input text :placeholder="$t('message.account.accountPlaceholder1')" v-model="state.myForm.username" clearable autocomplete="off">
				<template #prefix>
					<el-icon class="el-input__icon"><ele-User /></el-icon>
				</template>
			</el-input>
		</el-form-item>
		<el-form-item class="login-animation2" prop="password">
			<el-input
				:type="state.isShowPassword ? 'text' : 'password'"
				:placeholder="$t('message.account.accountPlaceholder2')"
				v-model="state.myForm.password"
				autocomplete="off"
			>
				<template #prefix>
					<el-icon class="el-input__icon"><ele-Unlock /></el-icon>
				</template>
				<template #suffix>
					<i
						class="iconfont el-input__icon login-content-password"
						:class="state.isShowPassword ? 'icon-yincangmima' : 'icon-xianshimima'"
						@click="state.isShowPassword = !state.isShowPassword"
					>
					</i>
				</template>
			</el-input>
		</el-form-item>
		<el-form-item class="login-animation3" prop="code">
			<el-col :span="15">
				<el-input
					text
					maxlength="4"
					:placeholder="$t('message.account.accountPlaceholder3')"
					v-model="state.myForm.code"
					clearable
					autocomplete="off"
				>
					<template #prefix>
						<el-icon class="el-input__icon"><ele-Position /></el-icon>
					</template>
				</el-input>
			</el-col>
			<el-col :span="1"></el-col>
			<el-col :span="8">
				<el-button class="login-content-code" v-waves><img class="login-content-code-img" alt="fastcms" @click="refreshCode" :src="state.captcha"></el-button>
			</el-col>
		</el-form-item>
		<el-form-item class="login-animation4">
			<el-button type="primary" class="login-content-submit" round v-waves @click="onSignIn" :loading="state.loading.signIn">
				<span>{{ $t('message.account.accountBtnText') }}</span>
			</el-button>
		</el-form-item>
	</el-form>
</template>

<script setup lang="ts" name="loginAccount">
import { reactive, computed, onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { useI18n } from 'vue-i18n';
// import Cookies from 'js-cookie';
import { initFrontEndControlRoutes } from '/@/router/frontEnd';
import { initBackEndControlRoutes } from '/@/router/backEnd';
import { Session, Local } from '/@/utils/storage';
import { formatAxis } from '/@/utils/formatTime';
import { NextLoading } from '/@/utils/loading';
import { LoginApi } from '/@/api/login/index';


// 定义变量内容
const myRefForm = ref();
const loginApi = LoginApi();
const { t } = useI18n();
const route = useRoute();
const router = useRouter();
const state = reactive({
	isShowPassword: false,
	captcha: '',
	captchaKey: '',
	myForm: {
		username: '',
		password: '',
		code: '',
	},
	rules: {
		username: { required: true, message: '请输入用户名', trigger: 'blur' },
		password: { required: true, message: '请输入密码', trigger: 'blur' },
		code: { required: true, message: '请输入验证码', trigger: 'blur' },
	},
	loading: {
		signIn: false,
	},
});

// 时间获取
const currentTime = computed(() => {
	return formatAxis(new Date());
});

const refreshCode = async () => {
	loginApi.getCaptcha().then(res => {
		state.captcha = res.data.image;
		state.captchaKey = res.data.codeUuid;
		Session.set('ClientId', state.captchaKey);
	}).catch(() => {
	});
};

// 登录
const onSignIn = async () => {

	new Promise((resolve) => {
		myRefForm.value.validate((valid: boolean) => {
			if (valid) {
				resolve(valid);
				state.loading.signIn = true;
				loginApi.signIn(state.myForm).then(async res => {
					Session.set('token', res.data.token);
					Session.set('tokenTtl', res.data.tokenTtl);
					Local.set('tokenTtl', res.data.tokenTtl);
					Local.set('token', res.data.token);
					// 模拟数据，对接接口时，记得删除多余代码及对应依赖的引入。用于 `/src/stores/userInfo.ts` 中不同用户登录判断（模拟数据）
					// Cookies.set('userName', state.ruleForm.userName);

					const userInfos = {
						username: res.data.username,
						photo: res.data.headImg === null ? '/header.jpg' : res.data.headImg,
						time: new Date().getTime(),
						hasRole: res.data.hasRole,
						version: res.data.version,
						userType: res.data.userType,
					};

					Local.set('userInfo', userInfos);
					Session.set('userInfo', userInfos);

					if(res.data.userType === 1) {
						// 后端管理用户初始化后端动态路由
						const isNoPower = await initBackEndControlRoutes();
						// 执行完 initBackEndControlRoutes，再执行 signInSuccess
						signInSuccess(isNoPower);
					} else if(res.data.userType === 2) {
						// 前台用户初始化前端静态路由
						const isNoPower = await initFrontEndControlRoutes();
						signInSuccess(isNoPower);
					}
					
				}).catch(error => {
					state.loading.signIn = false;
					refreshCode();
					ElMessage.error(error.message ? error.message : '系统错误' );
				})
			}
		});
	});
	
};
// 登录成功后的跳转
const signInSuccess = (isNoPower: boolean | undefined) => {
	if (isNoPower) {
		ElMessage.warning('抱歉，您没有登录权限');
		Session.clear();
		Local.clear();
	} else {
		// 初始化登录成功时间问候语
		let currentTimeInfo = currentTime.value;
		// 登录成功，跳到转首页
		// 如果是复制粘贴的路径，非首页/登录页，那么登录成功后重定向到对应的路径中
		if (route.query?.redirect) {
			router.push({
				path: <string>route.query?.redirect,
				query: Object.keys(<string>route.query?.params).length > 0 ? JSON.parse(<string>route.query?.params) : '',
			});
		} else {
			router.push('/');
		}
		// 登录成功提示
		const signInText = t('message.signInText');
		ElMessage.success(`${currentTimeInfo}，${signInText}`);
		// 添加 loading，防止第一次进入界面时出现短暂空白
		NextLoading.start();
	}
	state.loading.signIn = false;
};

onMounted(() => {
	refreshCode();
});

</script>

<style scoped lang="scss">
.login-content-form {
	margin-top: 20px;
	@for $i from 1 through 4 {
		.login-animation#{$i} {
			opacity: 0;
			animation-name: error-num;
			animation-duration: 0.5s;
			animation-fill-mode: forwards;
			animation-delay: calc($i/10) + s;
		}
	}
	.login-content-password {
		display: inline-block;
		width: 20px;
		cursor: pointer;
		&:hover {
			color: #909399;
		}
	}
	.login-content-code {
		width: 100%;
		padding: 0;
		font-weight: bold;
		letter-spacing: 5px;
	}
	.login-content-submit {
		width: 100%;
		letter-spacing: 2px;
		font-weight: 300;
		margin-top: 15px;
	}
}
</style>
