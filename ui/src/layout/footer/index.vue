<template>
	<div class="layout-footer mt15" v-show="isDelayFooter">
		<div class="layout-footer-warp">
			<div>{{ getUserInfos.version }}</div>
			<div class="mt5">{{public_website_copyright}}</div>
		</div>
	</div>
</template>

<script lang="ts">
import { computed, toRefs, reactive, onMounted } from 'vue';
import { onBeforeRouteUpdate } from 'vue-router';
import { useStore } from '/@/store/index';
import { useI18n } from 'vue-i18n';
import { getPublicConfigList } from '/@/api/config/index';
import qs from 'qs';
export default {
	name: 'layoutFooter',
	setup() {
		const store = useStore();
		const { t } = useI18n();
		const state = reactive({
			isDelayFooter: true,
			public_website_copyright: t('message.copyright.one5')
		});
		// 路由改变时，等主界面动画加载完毕再显示 footer
		onBeforeRouteUpdate(() => {
			state.isDelayFooter = false;
			setTimeout(() => {
				state.isDelayFooter = true;
			}, 800);
		});
		const getUserInfos = computed(() => {
			return store.state.userInfos.userInfos;
		});

		onMounted(() => {
			let paramKeys = new Array();
			paramKeys.push("public_website_copyright");
			let params = qs.stringify( {"configKeys" : paramKeys}, {arrayFormat: 'repeat'});
			getPublicConfigList(params).then((res) => {
				res.data.forEach(item => {
					if(item.key == 'public_website_copyright' && item.jsonValue != null) {
						state.public_website_copyright = item.jsonValue	
					}
				});
			});
		})

		return {
			getUserInfos,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
.layout-footer {
	width: 100%;
	display: flex;
	&-warp {
		margin: auto;
		color: var(--el-text-color-secondary);
		text-align: center;
		animation: logoAnimation 0.3s ease-in-out;
	}
}
</style>
