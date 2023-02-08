<template>
	<div class="login-container">
		<div class="login-logo">
			<span>{{ getThemeConfig.globalViceTitle }}</span>
		</div>
		<div class="login-content">
			<div class="login-content-main">
				<h4 class="login-content-title">{{ getThemeConfig.globalTitle }}</h4>
				<div>
					<el-form class="login-content-form" :model="myForm" :rules="rules" ref="myRefForm">
                        <el-form-item prop="username">
                            <el-input
                                type="text"
                                :placeholder="$t('message.account.accountPlaceholder1')"
                                prefix-icon="el-icon-user"
                                v-model="myForm.username"
                                clearable
                                autocomplete="off"
								@change="confirmUserName"
                            >
                            </el-input>
                        </el-form-item>
						<el-form-item prop="nickName">
                            <el-input
                                type="text"
                                :placeholder="$t('message.account.accountPlaceholder6')"
                                prefix-icon="el-icon-user"
                                v-model="myForm.nickName"
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
                        <el-form-item prop="repeatPassword">
                            <el-input
                                :type="isShowPassword ? 'text' : 'password'"
                                :placeholder="$t('message.account.accountPlaceholder4')"
                                prefix-icon="el-icon-lock"
                                v-model="myForm.repeatPassword"
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
                            <el-button type="primary" class="login-content-submit" round @click="onRegisterIn" :loading="loading.registerIn">
                                <span>{{ $t('message.account.accountRegistBtnText') }}</span>
                            </el-button>
                        </el-form-item>
                    </el-form>
					<div class="mt10">
						<el-button type="text" size="small" @click="toLogin">{{ $t('message.link.two5') }}</el-button>
						<!-- <el-button type="text" size="small">{{ $t('message.link.two4') }}</el-button> -->
					</div>
				</div>
			</div>
		</div>
		<div class="login-copyright">
			<div class="mb5 login-copyright-company">{{ $t('message.copyright.one5') }}</div>
			<div class="login-copyright-msg">{{ $t('message.copyright.two6') }}</div>
		</div>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, computed, getCurrentInstance, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { useI18n } from 'vue-i18n';
import { Session } from '/@/utils/storage';
import { formatAxis } from '/@/utils/formatTime';
import { useStore } from '/@/store/index';
import { register, getCaptcha } from '/@/api/login/index';
import qs from 'qs';

export default {
	name: 'register',
	setup() {
		const { t } = useI18n();
		const { proxy } = getCurrentInstance() as any;
		const store = useStore();
		const route = useRoute();
		const router = useRouter();
		const state = reactive({
            isShowPassword: false,
            captcha: '',
            myForm: {
				nickName: '',
				username: '',
				password: '',
                repeatPassword: '',
				code: '',
			},
            rules: {
				username: { required: true, message: '请输入账号', trigger: 'blur' },
				nickName: { required: true, message: '请输入用户昵称', trigger: 'blur' },
				password: { required: true, message: '请输入密码', trigger: 'blur' },
                repeatPassword: { required: true, message: '请输入确认密码', trigger: 'blur' },
                code: { required: true, message: '请输入验证码', trigger: 'blur' },
			},
            loading: {
				registerIn: false,
			},
		});
		// 获取布局配置信息
		const getThemeConfig = computed(() => {
			return store.state.themeConfig.themeConfig;
		});

        // 时间获取
		const currentTime = computed(() => {
			return formatAxis(new Date());
		});

        const refreshCode = async () => {
			getCaptcha().then((res) => {
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
		const onRegisterIn = async () => {

			new Promise((resolve) => {
				proxy.$refs['myRefForm'].validate((valid) => {
					if (valid) {
						resolve(valid);
						userRegister();
					}
				});
			});
		};

		const userRegister = async() => {
			register(qs.stringify(state.myForm)).then(() => {
				registerInSuccess();
			}).catch((res) => {
				refreshCode();
				ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
			})
		}

		// 登录成功后的跳转
		const registerInSuccess = () => {
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
				router.push('/login');
			}
			// 登录成功提示
			setTimeout(() => {
				// 关闭 loading
				state.loading.registerIn = true;
				const registerInText = t('message.registerInText');
				ElMessage.success(`${currentTimeInfo}，${registerInText}`);
			}, 300);
		};

        const toLogin = () => {
            router.push('/login');
        };

		const confirmUserName = () => {
			if (/[^a-z0-9_]/g.test(state.myForm.username)) {
				state.myForm.username = '';
				ElMessage({showClose: true, message: "只能输入英文字母数字", type: 'error'});
			}
		};

		return {
			getThemeConfig,
            currentTime,
			confirmUserName,
			onRegisterIn,
            toLogin,
			refreshCode,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
.login-container {
	width: 100%;
	height: 100%;
	background: url('/bg-login.png') no-repeat;
	background-size: 100% 100%;
	.login-logo {
		position: absolute;
		top: 30px;
		left: 50%;
		height: 50px;
		display: flex;
		align-items: center;
		font-size: 20px;
		color: var(--color-primary);
		letter-spacing: 2px;
		width: 90%;
		transform: translateX(-50%);
	}
	.login-content {
		width: 500px;
		padding: 20px;
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%) translate3d(0, 0, 0);
		background-color: rgba(255, 255, 255, 0.99);
		border: 5px solid var(--color-primary-light-8);
		border-radius: 4px;
		transition: height 0.2s linear;
		height: 480px;
		overflow: hidden;
		z-index: 1;
		.login-content-main {
			margin: 0 auto;
			width: 80%;
			.login-content-title {
				color: #333;
				font-weight: 500;
				font-size: 22px;
				text-align: center;
				letter-spacing: 4px;
				margin: 15px 0 30px;
				white-space: nowrap;
				z-index: 5;
				position: relative;
				transition: all 0.3s ease;
			}
		}
		.login-content-main-sacn {
			position: absolute;
			top: 0;
			right: 0;
			width: 50px;
			height: 50px;
			overflow: hidden;
			cursor: pointer;
			transition: all ease 0.3s;
			&-delta {
				position: absolute;
				width: 35px;
				height: 70px;
				z-index: 2;
				top: 2px;
				right: 21px;
				background: var(--el-color-white);
				transform: rotate(-45deg);
			}
			&:hover {
				opacity: 1;
				transition: all ease 0.3s;
				color: var(--color-primary);
			}
			i {
				width: 47px;
				height: 50px;
				display: inline-block;
				font-size: 48px;
				position: absolute;
				right: 2px;
				top: -1px;
			}
		}
	}
	.login-content-mobile {
		height: 418px;
	}
	.login-copyright {
		position: absolute;
		left: 50%;
		transform: translateX(-50%);
		bottom: 30px;
		text-align: center;
		color: var(--color-whites);
		font-size: 12px;
		opacity: 0.8;
		.login-copyright-company {
			white-space: nowrap;
		}
		.login-copyright-msg {
			@extend .login-copyright-company;
		}
	}
}
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
