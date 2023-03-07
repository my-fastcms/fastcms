<template>
	<div class="login-container">
		<div class="login-logo">
			<span>{{ getThemeConfig.globalViceTitle }}</span>
		</div>
		<div class="login-content" :class="{ 'login-content-mobile': tabsActiveName === 'mobile' }">
			<div class="login-content-main">
				<h4 class="login-content-title">{{ getThemeConfig.globalTitle }}</h4>
				<div v-if="!isScan">
					<el-tabs v-model="tabsActiveName" @tab-click="onTabsClick">
						<el-tab-pane :label="$t('message.label.one1')" name="account" :disabled="tabsActiveName === 'account'">
							<transition name="el-zoom-in-center">
								<Account v-show="isTabPaneShow" />
							</transition>
						</el-tab-pane>
						 
						<!-- <el-tab-pane :label="$t('message.label.two2')" name="mobile" :disabled="tabsActiveName === 'mobile'" ref="mobileTab">
							<transition name="el-zoom-in-center">
								<Mobile v-show="!isTabPaneShow" />
							</transition>
						</el-tab-pane> -->
						
					</el-tabs>
					<div class="mt10">
						<el-button type="text" size="small" @click="toRegister" v-if="public_register_enable">{{ $t('message.link.one3') }}</el-button>
						<el-button type="text" size="small" @click="toRestPassword" v-if="true">{{ $t('message.link.two6') }}</el-button>
						<el-button type="text" size="small" @click="toWechatMpOAuth" v-if="isWechatBrowser">{{ $t('message.link.two7') }}</el-button>
					</div>
				</div>
				<Scan v-else />
				
				<div class="login-content-main-sacn" v-if="true" @click="isScan = !isScan" ref="scanDiv">
					<i class="iconfont" :class="isScan ? 'icon-diannao1' : 'icon-barcode-qr'"></i>
					<div class="login-content-main-sacn-delta"></div>
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
import { toRefs, reactive, computed, onMounted } from 'vue';
import Account from '/@/views/login/component/account.vue';
import Mobile from '/@/views/login/component/mobile.vue';
import Scan from '/@/views/login/component/scan.vue';
import { useStore } from '/@/store/index';
import { useRouter } from 'vue-router';
import qs from 'qs';
import { getPublicConfigList } from '/@/api/config/index';

export default {
	name: 'login',
	components: { Account, Mobile, Scan },
	setup() {
		const store = useStore();
		const router = useRouter();
		const state = reactive({
			tabsActiveName: 'account',
			isTabPaneShow: true,
			isScan: false,
			public_register_enable: false,
			isWechatBrowser: false,
			public_website_domain: ''
		});
		// 获取布局配置信息
		const getThemeConfig = computed(() => {
			return store.state.themeConfig.themeConfig;
		});
		// 切换密码、手机登录
		const onTabsClick = () => {
			state.isTabPaneShow = !state.isTabPaneShow;
		};
		const toRegister = () => {
			router.push('/register');
		}
		const toRestPassword = () => {
			router.push('/rest/password');
		}
		const toWechatMpOAuth = () => {
			console.log("===========toWechatMpOAuth")
			console.log("url:" + state.public_website_domain + "/oauth2/authorization/wechat-mp")
			window.location.href = state.public_website_domain + "/oauth2/authorization/wechat-mp"
		}
		const judgeWechatBrowser = () => {
			const ua = window.navigator.userAgent.toLowerCase();
			const match = ua.match(/MicroMessenger/i);
			if (match === null) {
				return false;
			}
			if (match.includes('micromessenger')) {
				return true;
			}
			return false;
		}

		onMounted(() => {
			state.isWechatBrowser = judgeWechatBrowser();
			let paramKeys = new Array();
			paramKeys.push("public_register_enable");
			paramKeys.push("public_website_domain");
			let params = qs.stringify( {"configKeys" : paramKeys}, {arrayFormat: 'repeat'});
			getPublicConfigList(params).then((res) => {
				res.data.forEach(item => {
					if(item.key == 'public_register_enable') {
						state.public_register_enable = item.jsonValue	
					}
					if(item.key == 'public_website_domain') {
						state.public_website_domain = item.jsonValue
					}
				});
			});
		})

		return {
			toRegister,
			toRestPassword,
			judgeWechatBrowser,
			toWechatMpOAuth,
			onTabsClick,
			getThemeConfig,
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
</style>
