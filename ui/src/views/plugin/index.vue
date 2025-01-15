<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-upload
					:action="state.uploadUrl"
					name="file"
					:headers="state.headers"
					:show-file-list="false"
					:on-success="uploadSuccess"
					:on-error="onHandleUploadError"
					:on-exceed="onHandleExceed"
					accept=".jar,.zip">
					<el-button class="mt15" type="primary" size="default"><el-icon><ele-Plus /></el-icon>安装插件</el-button>
				</el-upload>
			</div>
			<el-table :data="state.tableData.data" stripe style="width: 100%">
				<el-table-column prop="pluginId" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="pluginClass" label="插件名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="provider" label="作者" show-overflow-tooltip></el-table-column>
				<el-table-column prop="pluginState" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="180">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="beforeOnRowConfig(scope.row)">配置</el-button>
						<el-button size="small" text type="primary" @click="onRowUnInstall(scope.row)">卸载</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				@size-change="onHandleSizeChange"
				@current-change="onHandleCurrentChange"
				class="mt15"
				:pager-count="5"
				:page-sizes="[10, 20, 30]"
				v-model:current-page="state.tableData.param.pageNum"
				background
				v-model:page-size="state.tableData.param.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="state.tableData.total"
			>
			</el-pagination>
		</el-card>

		<el-dialog
			title="插件配置"
			fullscreen
			:model-value="state.dialogVisible"
			:before-close="handleClose"
      @opened="onRowConfig"
		>
			<iframe :src="state.pluginConfigUrl" frameborder="0" style="width:100%;height:calc(100vh - 5px)" ref="iframeRef"></iframe>
		</el-dialog>

	</div>
</template>

<script setup lang="ts" name="pluginManager">
import {ElMessage, ElMessageBox} from 'element-plus';
import {onMounted, reactive, ref} from 'vue';
import {PluginApi} from '/@/api/plugin/index';
import {Local} from '/@/utils/storage';

const iframeRef = ref<HTMLElement | null>(null); // iframe ref
const pluginApi = PluginApi();
const state = reactive({
	dialogVisible: false,
	pluginConfigUrl: '',
	limit: 1,
	uploadUrl: import.meta.env.VITE_API_URL + "/admin/plugin/install",
	headers: {"Authorization": Local.get('token')},
	tableData: {
		data: [],
		total: 0,
		loading: false,
		param: {
			pageNum: 1,
			pageSize: 10,
		},
	},
});
// 初始化表格数据
const initTableData = () => {
	pluginApi.getPluginList(state.tableData.param).then((res) => {
		state.tableData.data = res.data.records;
		state.tableData.total = res.data.total;
	}).catch(() => {
	})
};

const onRowUnInstall = (row: any) => {
	ElMessageBox.confirm('此操作将卸载插件, 是否继续?', '提示', {
		confirmButtonText: '卸载',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		pluginApi.unInstallPlugin(row.pluginId).then(() => {
			ElMessage.success("卸载成功");
			initTableData();
		}).catch((res) => {
			ElMessage.error(res.message);
		});
	})
	.catch(() => {});
};

let currentConfigRow: any | null = null;
const beforeOnRowConfig = (row: any) => {
currentConfigRow = row;
state.dialogVisible = true;
};

const onRowConfig = () => {
	if (!currentConfigRow) return;
	pluginApi.getPluginConfigUrl(currentConfigRow.pluginId).then((res) => {
		state.pluginConfigUrl = res.data + "?accessToken=" + Local.get('token');
		// state.pluginConfigUrl = "public/testIframe.html";
		console.log("=====url:" + state.pluginConfigUrl);

		const iframe = iframeRef.value;
		if(iframe && iframe.tagName.toUpperCase() === "IFRAME") {
			// const postData = "testTokenString";
	
			// iframe.postMessage({cmd:'sendToken', params:{token: postData}}, '*');
			// iframe.onload = function() {
			// 	iframe.contentWindow.document.getElementById("token").innerText = postData;
			// 	iframe.contentWindow.getToken(postData);
			// }
		}

	}).catch((e) => {
		ElMessage.error("插件不支持配置" + e);
	})

};

const handleClose = () => {
	state.dialogVisible = false;
};

// 分页改变
const onHandleSizeChange = (val: number) => {
	state.tableData.param.pageSize = val;
	initTableData();
};
// 分页改变
const onHandleCurrentChange = (val: number) => {
	state.tableData.param.pageNum = val;
	initTableData();
};

const uploadSuccess = (res :any) => {
	if(res.code == 200) {
		ElMessage.success("安装成功");
		initTableData();
	}else {
		ElMessage.error(res.message);
	}
}
const onHandleUploadError = (error: any) => {
	ElMessage.error("安装失败," + error);
}
const onHandleExceed = () => {

}

// 页面加载时
onMounted(() => {
	initTableData();
});
</script>

<style scoped lang="scss">
</style>
