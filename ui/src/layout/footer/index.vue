<template>
	<div class="layout-footer mt15" v-show="isDelayFooter">
		<div class="layout-footer-warp">
			<div>{{ getUserInfos.version }}</div>
			<div class="mt5">{{ $t('message.copyright.one5') }}</div>
		</div>
	</div>
</template>

<script lang="ts">
import { computed, toRefs, reactive } from 'vue';
import { onBeforeRouteUpdate } from 'vue-router';
import { useStore } from '/@/store/index';
export default {
	name: 'layoutFooter',
	setup() {
		const store = useStore();
		const state = reactive({
			isDelayFooter: true,
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
