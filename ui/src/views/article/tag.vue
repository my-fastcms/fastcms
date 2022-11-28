<template>
	<div class="system-Category-container">
		<el-card shadow="hover">
			<el-button @click="onOpenAddTag" class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">新建标签</el-button>
			<el-table :data="menuTableData" stripe style="width: 100%" row-key="id">
				<el-table-column prop="tagName" label="名称" show-overflow-tooltip>
					<template #default="scope">
					<a :href="scope.row.url" target="_blank">
						{{scope.row.tagName}}
					</a></template>
				</el-table-column>
				<el-table-column prop="id" label="id" show-overflow-tooltip></el-table-column>
				<el-table-column prop="suffix" label="模板" show-overflow-tooltip></el-table-column>
				<el-table-column prop="sortNum" label="排序" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="125">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onOpenEditTag(scope.row)">修改</el-button>
						<el-button size="mini" type="text" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<AddTag ref="addTagRef" @reloadTable="loadTagList"/>
		<EditTag ref="editTagRef" @reloadTable="loadTagList"/>
	</div>
</template>

<script lang="ts">
import { ref, toRefs, reactive, computed, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getArticleTagList, delArticleTag } from '/@/api/article/index';
import AddTag from '/@/views/article/component/addTag.vue';
import EditTag from '/@/views/article/component/editTag.vue';

export default {
	name: 'articleTag',
	components: { AddTag, EditTag },
	setup() {
		const addTagRef = ref();
		const editTagRef = ref();
		const state = reactive({
			menuData: null
		});
		// 获取 vuex 中的路由
		const menuTableData = computed(() => {
			return state.menuData;
		});
		// 打开新增菜单弹窗
		const onOpenAddTag = (row: object) => {
			addTagRef.value.openDialog(row);
		};
		// 打开编辑菜单弹窗
		const onOpenEditTag = (row: object) => {
			editTagRef.value.openDialog(row);
		};
		// 删除当前行
		const onTabelRowDel = (row: any) => {
			ElMessageBox.confirm('此操作将永久删除标签, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				console.log(row);
				delArticleTag(row.id).then(() => {
					ElMessage.success("删除成功");
					loadTagList();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			}).catch(()=> {})
		};

		const loadTagList = () => {
			getArticleTagList().then((res) => {
				state.menuData = res.data;
			}).catch(() => {
			})
		}

		onMounted(() => {
			loadTagList();
		});

		return {
			addTagRef,
			editTagRef,
			onOpenAddTag,
			onOpenEditTag,
			menuTableData,
			onTabelRowDel,
			loadTagList,
			...toRefs(state),
		};
	},
};
</script>
