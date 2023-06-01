<template>
	<div class="layout-footer pb15" style="padding-top: 10px;">
		<div class="layout-footer-warp">
			<div>{{ userInfos.version }}</div>
			<div class="mt5">广州小橘灯信息科技有限公司</div>
		</div>
	</div>
</template>

<script setup lang="ts" name="layoutFooter">
import { storeToRefs } from 'pinia';
import { useUserInfo } from '/@/stores/userInfo';

const stores = useUserInfo();
const { userInfos } = storeToRefs(stores);
</script>

<style scoped lang="scss">
.layout-footer {
	width: 100%;
	display: flex;
	&-warp {
		margin: auto;
		color: var(--el-text-color-secondary);
		text-align: center;
		animation: error-num 0.3s ease;
	}
}
</style>
