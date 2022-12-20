<template>
	<el-form class="login-content-form" :model="myForm" :rules="rules" ref="myRefForm">
		<el-form-item prop="username">
			<el-input
				type="text"
				:placeholder="$t('message.account.accountPlaceholder1')"
				prefix-icon="el-icon-user"
				v-model="myForm.username"
				clearable
				autocomplete="off"
			>
			</el-input>
		</el-form-item>
		<el-form-item prop="password">
			<el-input
				:type="isShowPassword ? 'text' : 'password'"
				:placeholder="$t('message.account.accountPlaceholder2')"
				prefix-icon="el-icon-lock"
				v-model="myForm.password"
				autocomplete="off"
			>
				<template #suffix>
					<i
						class="iconfont el-input__icon login-content-password"
						:class="isShowPassword ? 'icon-yincangmima' : 'icon-xianshimima'"
						@click="isShowPassword = !isShowPassword"
					>
					</i>
				</template>
			</el-input>
		</el-form-item>
		<el-form-item prop="code">
			<el-row :gutter="15">
				<el-col :span="14">
					<el-input
						type="text"
						maxlength="5"
						:placeholder="$t('message.account.accountPlaceholder3')"
						prefix-icon="el-icon-position"
						v-model="myForm.code"
						clearable
						autocomplete="off"
					></el-input>
				</el-col>
				<el-col :span="10">
					<div class="login-content-code">
						<img class="login-content-code-img" alt="fastcms" @click="refreshCode" :src="captcha">
					</div>
				</el-col>
			</el-row>
		</el-form-item>
		<el-form-item>
			<el-button type="primary" class="login-content-submit" round @click="onSignIn" :loading="loading.signIn">
				<span>{{ $t('message.account.accountBtnText') }}</span>
			</el-button>
		</el-form-item>
	</el-form>
</template>

<script lang="ts">
import { toRefs, reactive, defineComponent, computed, getCurrentInstance, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { dynamicRoutes } from '/@/router/route';
import { useI18n } from 'vue-i18n';
import { initFrontEndControlRoutes } from '/@/router/frontEnd';
import { initBackEndControlRoutes } from '/@/router/backEnd';
import { useStore } from '/@/store/index';
import { Local, Session } from '/@/utils/storage';
import { formatAxis } from '/@/utils/formatTime';
import { signIn, getCaptcha } from '/@/api/login/index';
import qs from 'qs';

export default defineComponent({
	name: 'login',
	setup() {
		const { t } = useI18n();
		const { proxy } = getCurrentInstance() as any;
		const store = useStore();
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
			getCaptcha().then(res => {
				state.captcha = res.data.image;
				state.captchaKey = res.data.codeUuid;
				Session.set('ClientId', state.captchaKey);
			}).catch(() => {
			});
		};

		onMounted(() => {
			refreshCode();
		});

		// 登录
		const onSignIn = async () => {

			new Promise((resolve) => {
				proxy.$refs['myRefForm'].validate((valid) => {
					if (valid) {
						resolve(valid);
						signLogin();
					}
				});
			});
		};

		const signLogin = async() => {
			state.loading.signIn = true;
			signIn(qs.stringify(state.myForm)).then(res => {
				state.loading.signIn = false;
				doLogin(res);
			}).catch((res) => {
				state.loading.signIn = false;
				refreshCode();
				ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			})
		}

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
		};
		return {
			currentTime,
			onSignIn,
			refreshCode,
			...toRefs(state),
		};
	},
});
</script>

<style scoped lang="scss">
.login-content-form {
	margin-top: 20px;
	.login-content-password {
		display: inline-block;
		width: 25px;
		cursor: pointer;
		&:hover {
			color: #909399;
		}
	}
	.login-content-code {
		display: flex;
		align-items: center;
		justify-content: space-around;
		.login-content-code-img {
			width: 100%;
			height: 40px;
			line-height: 40px;
			background-color: #ffffff;
			border: 1px solid rgb(220, 223, 230);
			color: #333;
			font-size: 16px;
			font-weight: 700;
			letter-spacing: 5px;
			text-indent: 5px;
			text-align: center;
			cursor: pointer;
			transition: all ease 0.2s;
			border-radius: 4px;
			user-select: none;
			&:hover {
				border-color: #c0c4cc;
				transition: all ease 0.2s;
			}
		}
	}
	.login-content-submit {
		width: 100%;
		letter-spacing: 2px;
		font-weight: 300;
		margin-top: 15px;
	}
}
</style>
