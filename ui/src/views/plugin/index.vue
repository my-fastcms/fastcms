<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-upload 
					:action="uploadUrl"
					name="file"
					:headers="headers"
					:show-file-list="false"
					:on-success="uploadSuccess"
					:on-error="onHandleUploadError"
					:on-exceed="onHandleExceed"
					accept=".jar">
					<el-button class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">安装插件</el-button>
				</el-upload>
			</div>
			<el-table :data="tableData.data" stripe style="width: 100%">
				<el-table-column prop="pluginId" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="pluginClass" label="插件名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="provider" label="作者" show-overflow-tooltip></el-table-column>
				<el-table-column prop="pluginState" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="90">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onRowConfig(scope.row)">配置</el-button>
						<el-button size="mini" type="text" @click="onRowUnInstall(scope.row)">卸载</el-button>
					</template>
				</el-table-column>
			</el-table>
			<el-pagination
				@size-change="onHandleSizeChange"
				@current-change="onHandleCurrentChange"
				class="mt15"
				:pager-count="5"
				:page-sizes="[10, 20, 30]"
				v-model:current-page="tableData.param.pageNum"
				background
				v-model:page-size="tableData.param.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="tableData.total"
			>
			</el-pagination>
		</el-card>
  
		<el-dialog
			title="插件配置"
			fullscreen
			:model-value="dialogVisible"
			:before-close="handleClose"
		>
			<iframe :src="pluginConfigUrl" frameborder="0" style="width:100%;height:600px"></iframe>
		</el-dialog>

	</div>
</template>

<script lang="ts">
import { ElMessageBox, ElMessage } from 'element-plus';
import { toRefs, reactive, onMounted } from 'vue';
import { getPluginList, unInstallPlugin, getPluginConfigUrl } from '/@/api/plugin/index';
import { Session } from '/@/utils/storage';
export default {
	name: 'pluginManager',
	setup() {
		const state = reactive({
			dialogVisible: false,
			pluginConfigUrl: '',
			limit: 1,
			uploadUrl: import.meta.env.VITE_API_URL + "/admin/plugin/install",
			headers: {"Authorization": Session.get('token')},
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
			getPluginList(state.tableData.param).then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			}).catch(() => {
			})
		};
		
		const onRowUnInstall = (row: object) => {
			ElMessageBox.confirm('此操作将卸载插件, 是否继续?', '提示', {
				confirmButtonText: '卸载',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				unInstallPlugin(row.pluginId).then(() => {
					ElMessage.success("卸载成功");
					initTableData();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			})
			.catch(() => {});
		};

		const onRowConfig = (row: object) => {
			getPluginConfigUrl(row.pluginId).then((res) => {
				state.pluginConfigUrl = res.data;
				state.dialogVisible = true;
			}).catch(() => {
				ElMessage.error("插件不支持配置");
			})
			
		};

		const handleClose = () => {
			state.dialogVisible = false;
		};

		// 分页改变
		const onHandleSizeChange = (val: number) => {
			state.tableData.param.pageSize = val;
		};
		// 分页改变
		const onHandleCurrentChange = (val: number) => {
			state.tableData.param.pageNum = val;
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
		return {
			handleClose,
			onRowConfig,
			onRowUnInstall,
			onHandleSizeChange,
			onHandleCurrentChange,
			initTableData,
			uploadSuccess,
			onHandleUploadError,
			onHandleExceed,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
</style>
