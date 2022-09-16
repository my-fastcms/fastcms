<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<!-- <el-button class="mt15" size="small" @click="addArticle()" type="primary" icon="iconfont icon-shuxingtu">新增评论</el-button> -->
				<el-input size="small" placeholder="请输入评论内容" prefix-icon="el-icon-search" style="max-width: 180px" class="ml10"></el-input>
				<el-button size="small" type="primary" class="ml10">查询</el-button>
			</div>
			<el-table :data="tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="content" label="评论内容" show-overflow-tooltip></el-table-column>
				<el-table-column prop="parentComment" label="回复评论" show-overflow-tooltip></el-table-column>
				<el-table-column prop="author" label="评论人" show-overflow-tooltip></el-table-column>
				<el-table-column prop="articleTitle" label="评论文章" show-overflow-tooltip></el-table-column>
				<el-table-column prop="status" label="状态" show-overflow-tooltip></el-table-column>
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
		<EditComment ref="editCommentRef" @reloadTable="initTableData"/>
	</div>
</template>

<script lang="ts">
import { ElMessageBox, ElMessage } from 'element-plus';
import { toRefs, ref, reactive, onMounted } from 'vue';
import { getArticleCommentList, delArticleComment } from '/@/api/article/index';

import EditComment from '/@/views/article/component/editComment.vue';

export default {
	name: 'articleCommentManager',
	components: { EditComment },
	setup() {

		const editCommentRef = ref();

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
			getArticleCommentList(state.tableData.param).then((res) => {
				state.tableData.data = res.data.records;
				state.tableData.total = res.data.total;
			}).catch(() => {
			})
		};
		// 当前行删除
		const onRowDel = (row: object) => {
			ElMessageBox.confirm('此操作将永久删除评论, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				delArticleComment(row.id).then(() => {
					ElMessage.success("删除成功");
					initTableData();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			})
			.catch(() => {});
		};

        const onRowUpdate = (row: object) => {
            console.log(row);
			editCommentRef.value.openDialog(row);
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
        const addArticle = () => {
			//router.push({ path: '/article/write'});
        };
		// 页面加载时
		onMounted(() => {
			initTableData();
		});
		return {
			editCommentRef,
            addArticle,
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
