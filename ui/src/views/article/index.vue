<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-input size="default" v-model="state.tableData.param.title" placeholder="请输入文章标题" prefix-icon="el-icon-search" style="max-width: 180px" class="ml10"></el-input>
				<el-cascader size="default" v-model="state.tableData.param.categoryId" :options="state.categoryList" :props="{ checkStrictly: true, label: 'label', value: 'id', children: 'children' }" placeholder="分类" clearable class="ml10"/>
				<el-select size="default" v-model="state.tableData.param.tagId" clearable placeholder="标签" class="ml10">
					<el-option
						v-for="item in state.tagList"
						:key="item.id"
						:label="item.tagName"
						:value="item.id"
					/>
				</el-select>
				<el-select size="default" style="max-width: 180px" v-model="state.tableData.param.status" placeholder="请选择状态" clearable class="ml10">
					<el-option label="发布" value="publish"></el-option>
					<el-option label="草稿" value="draft"></el-option>
					<el-option label="审核" value="audit"></el-option>
					<el-option label="删除" value="delete"></el-option>
				</el-select>
				<el-button size="default" type="primary" class="ml10" @click="initTableData"><el-icon><ele-Search /></el-icon>查询</el-button>
				<el-button size="default" @click="addArticle()" type="primary"><el-icon><ele-Plus /></el-icon>新建文章</el-button>
			</div>
			<el-table :data="state.tableData.data" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip width="60"></el-table-column>
				<el-table-column prop="title" label="标题" show-overflow-tooltip>
					<template #default="scope">
					<a :href="scope.row.url" target="_blank">
						{{scope.row.title}}
					</a></template>
				</el-table-column>
				<el-table-column prop="categoryList" label="分类" show-overflow-tooltip>
					<template #default="scope">
						<span v-for="item in scope.row.categoryList" :key="item.id">{{item.title}},</span>
					</template>
				</el-table-column>
				<el-table-column prop="tagList" label="标签" show-overflow-tooltip>
					<template #default="scope">
						<span v-for="item in scope.row.tagList" :key="item.id">{{item.tagName}},</span>
					</template>
				</el-table-column>
				<el-table-column prop="author" label="作者" show-overflow-tooltip></el-table-column>
				<el-table-column prop="statusStr" label="状态" show-overflow-tooltip></el-table-column>
				<el-table-column prop="viewCount" label="浏览次数" show-overflow-tooltip></el-table-column>
                <el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="操作" width="120">
					<template #default="scope">
						<el-button size="small" text type="primary" @click="onRowUpdate(scope.row)">修改</el-button>
						<el-button size="small" text type="primary" @click="onRowDel(scope.row)">删除</el-button>
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
	</div>
</template>

<script lang="ts" setup name="articleManager">
import { ElMessageBox, ElMessage } from 'element-plus';
import { reactive, onMounted } from 'vue';
import { ArticleApi } from '/@/api/article/index';
import { useRouter } from 'vue-router';

const articleApi = ArticleApi();
const router = useRouter();

const state = reactive({
	tagList: [],
	categoryList: [],
	tableData: {
		data: [],
		total: 0,
		loading: false,
		param: {
			status: 'publish',
			categoryId: '',
			tagId: '',
			title: '',
			pageNum: 1,
			pageSize: 10,
		},
	},
});

// 初始化表格数据
const initTableData = () => {
	const categoryIdArray = state.tableData.param.categoryId;
	if(categoryIdArray != null && categoryIdArray.length>0) {
		state.tableData.param.categoryId = categoryIdArray[categoryIdArray.length - 1];
	}
	articleApi.getArticleList(state.tableData.param).then((res) => {
		state.tableData.data = res.data.records;
		state.tableData.total = res.data.total;
	}).catch(() => {
	})
};
// 当前行删除
const onRowDel = (row: any) => {
	ElMessageBox.confirm('此操作将永久删除文章, 是否继续?', '提示', {
		confirmButtonText: '删除',
		cancelButtonText: '取消',
		type: 'warning',
	}).then(() => {
		articleApi.delArticle(row.id).then(() => {
			ElMessage.success("删除成功");
			initTableData();
		}).catch((res) => {
			ElMessage.error(res.message);
		});
	})
	.catch(() => {});
};

const onRowUpdate = (row: any) => {
	router.push({ 
		path: '/article/write',
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
const addArticle = () => {
	router.push({ path: '/article/write'});
};

// 页面加载时
onMounted(() => {
	initTableData();
	articleApi.getArticleCategoryList().then((res) => {
		state.categoryList = res.data;
	})
	articleApi.getArticleTagList().then((res) => {
		state.tagList = res.data;
	})
});
</script>
