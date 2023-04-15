<template>
	<div class="layout-logo" v-if="setShowLogo" @click="onThemeConfigChange">
		<img src="/logo-mini.png" v-if="showFastcmsLog" class="layout-logo-medium-img" />
		<span>{{ website_title }}</span>
	</div>
	<div class="layout-logo-size" v-else @click="onThemeConfigChange">
		<img src="/logo-mini.png" class="layout-logo-size-img" />
	</div>
</template>

<script lang="ts">
import { computed, toRefs, getCurrentInstance, reactive, onMounted } from 'vue';
import { useStore } from '/@/store/index';
import { getConfigList } from '/@/api/config/index';
import qs from 'qs';
export default {
	name: 'layoutLogo',
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const store = useStore();

				// 获取布局配置信息
		const getThemeConfig = computed(() => {
			return store.state.themeConfig.themeConfig;
		});

		const state = reactive({
			isDelayFooter: true,
			showFastcmsLog: false,
			website_title: store.state.themeConfig.themeConfig.globalTitle
		});
		
		// 设置 logo 的显示。classic 经典布局默认显示 logo
		const setShowLogo = computed(() => {
			let { isCollapse, layout } = store.state.themeConfig.themeConfig;
			return !isCollapse || layout === 'classic' || document.body.clientWidth < 1000;
		});
		// logo 点击实现菜单展开/收起
		const onThemeConfigChange = () => {
			if (store.state.themeConfig.themeConfig.layout === 'transverse') return false;
			proxy.mittBus.emit('onMenuClick');
			store.state.themeConfig.themeConfig.isCollapse = !store.state.themeConfig.themeConfig.isCollapse;
		};
		onMounted(() => {
			let paramKeys = new Array();
			paramKeys.push("public_website_title");
			paramKeys.push("is_show_fastcms_logo");
			let params = qs.stringify( {"configKeys" : paramKeys}, {arrayFormat: 'repeat'});
			getConfigList(params).then((res) => {
				res.data.forEach(item => {
					if(item.key == 'public_website_title' && item.jsonValue != null) {
						state.website_title = item.jsonValue;	
					}
					if (item.key == 'is_show_fastcms_logo' && item.jsonValue != null) {
						state.showFastcmsLog = item.jsonValue;
					}
				});
			});
		});
		return {
			...toRefs(state),
			setShowLogo,
			getThemeConfig,
			onThemeConfigChange,
		};
	},
};
</script>

<style scoped lang="scss">
.layout-logo {
	width: 220px;
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: rgb(0 21 41 / 2%) 0px 1px 4px;
	color: var(--color-primary);
	font-size: 16px;
	cursor: pointer;
	animation: logoAnimation 0.3s ease-in-out;
	&:hover {
		span {
			color: var(--color-primary-light-2);
		}
	}
	&-medium-img {
		width: 20px;
		margin-right: 5px;
	}
}
.layout-logo-size {
	width: 100%;
	height: 50px;
	display: flex;
	cursor: pointer;
	animation: logoAnimation 0.3s ease-in-out;
	&-img {
		width: 20px;
		margin: auto;
	}
	&:hover {
		img {
			animation: logoAnimation 0.3s ease-in-out;
		}
	}
}
</style>
