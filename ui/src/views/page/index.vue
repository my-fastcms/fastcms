<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-button class="mt15" size="small" @click="addPage()" type="primary" icon="iconfont icon-shuxingtu">新建页面</el-button>
				<el-input size="small" placeholder="请输入标题" prefix-icon="el-icon-search" style="max-width: 180px" class="ml10"></el-input>
				<el-button size="small" type="primary" class="ml10">查询</el-button>
			</div>
			<el-table :data="tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="title" label="标题" show-overflow-tooltip>
					<template #default="scope">
					<a :href="scope.row.url" target="_blank">
						{{scope.row.title}}
					</a></template>
				</el-table-column>
				<el-table-column prop="author" label="作者" show-overflow-tooltip></el-table-column>
				<el-table-column prop="status" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="viewCount" label="浏览次数" show-overflow-tooltip></el-table-column>
                <el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="90">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onRowUpdate(scope.row)">修改</el-button>
						<el-button size="mini" type="text" @click="onRowDel(scope.row)">删除</el-button>
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
import { getPageList, delPage } from '/@/api/page/index';
import { useRouter } from 'vue-router';
export default {
	name: 'pageManager',
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

        const router = useRouter();
		// 初始化表格数据
		const initTableData = () => {
			getPageList(state.tableData.param).then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			}).catch(() => {
			})
		};
		// 当前行删除
		const onRowDel = (row: object) => {
			ElMessageBox.confirm('此操作将永久删除页面, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				delPage(row.id).then(() => {
					ElMessage.success("删除成功");
					initTableData();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			})
			.catch(() => {});
		};

        const onRowUpdate = (row: object) => {
            router.push({ 
                path: '/page/write',
                query: { id: row.id }
            });
        }

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
        const addPage = () => {
            router.push({ path: '/page/write'});
        };
		// 页面加载时
		onMounted(() => {
			initTableData();
		});
		return {
            addPage,
            onRowUpdate,
			onRowDel,
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
