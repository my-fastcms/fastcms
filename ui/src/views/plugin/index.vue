<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-button class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">安装插件</el-button>
				<el-input size="small" placeholder="请插件标题" prefix-icon="el-icon-search" style="max-width: 180px" class="ml10"></el-input>
				<el-button size="small" type="primary" class="ml10">查询</el-button>
			</div>
			<el-table :data="tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="title" label="插件名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="author" label="作者" show-overflow-tooltip></el-table-column>
				<el-table-column prop="status" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="viewCount" label="浏览次数" show-overflow-tooltip></el-table-column>
                <el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="90">
					<template #default="scope">
						<el-button v-if="scope.row.id != 1" size="mini" type="text" @click="onRowUnInstall(scope.row)">卸载</el-button>
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
	</div>
</template>

<script lang="ts">
import { ElMessageBox, ElMessage } from 'element-plus';
import { toRefs, reactive, onMounted } from 'vue';
import { getPluginList, unInstallPlugin } from '/@/api/plugin/index';
export default {
	name: 'pluginManager',
	setup() {
		const state = reactive({
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
				unInstallPlugin(row.id).then(() => {
					ElMessage.success("卸载成功");
					initTableData();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			})
			.catch(() => {});
		};

		// 分页改变
		const onHandleSizeChange = (val: number) => {
			state.tableData.param.pageSize = val;
		};
		// 分页改变
		const onHandleCurrentChange = (val: number) => {
			state.tableData.param.pageNum = val;
		};
		// 页面加载时
		onMounted(() => {
			initTableData();
		});
		return {
			onRowUnInstall,
			onHandleSizeChange,
			onHandleCurrentChange,
			initTableData,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
</style>
