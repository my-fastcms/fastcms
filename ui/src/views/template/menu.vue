<template>
	<div class="system-menu-container">
		<el-card shadow="hover">
			<el-button @click="onOpenAddMenu" class="mt15" size="small" type="primary" icon="iconfont icon-shuxingtu">新建菜单</el-button>
			<el-table :data="menuTableData" stripe style="width: 100%" row-key="id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
				<el-table-column prop="menuName" label="名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="menuUrl" label="跳转地址" show-overflow-tooltip></el-table-column>
				<el-table-column prop="sortNum" label="排序" show-overflow-tooltip></el-table-column>
				<el-table-column prop="target" label="打开方式" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="125">
					<template #default="scope">
						<el-button size="mini" type="text" @click="onOpenAddMenu(scope.row)">新增</el-button>
						<el-button size="mini" type="text" @click="onOpenEditMenu(scope.row)">修改</el-button>
						<el-button size="mini" type="text" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<AddMenu ref="addMenuRef" @reloadTable="loadMenuList"/>
		<EditMenu ref="editMenuRef" @reloadTable="loadMenuList"/>
	</div>
</template>

<script lang="ts">
import { ref, toRefs, reactive, computed, onMounted } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getTemplateMenuList, delTemplateMenu } from '/@/api/template/index';
import AddMenu from '/@/views/template/component/addMenu.vue';
import EditMenu from '/@/views/template/component/editMenu.vue';

export default {
	name: 'templateMenu',
	components: { AddMenu, EditMenu },
	setup() {
		const addMenuRef = ref();
		const editMenuRef = ref();
		const state = reactive({
			menuData: null
		});
		// 获取 vuex 中的路由
		const menuTableData = computed(() => {
			return state.menuData;
		});
		// 打开新增菜单弹窗
		const onOpenAddMenu = (row: object) => {
			addMenuRef.value.openDialog(row);
		};
		// 打开编辑菜单弹窗
		const onOpenEditMenu = (row: object) => {
			editMenuRef.value.openDialog(row);
		};
		// 删除当前行
		const onTabelRowDel = (row: any) => {
			ElMessageBox.confirm('此操作将永久删除菜单, 是否继续?', '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				console.log(row);
				delTemplateMenu(row.id).then(() => {
					ElMessage.success("删除成功");
					loadMenuList();
				}).catch((res) => {
					ElMessage.error(res.message);
				});
			}).catch(()=> {})
		};

		const loadMenuList = () => {
			getTemplateMenuList().then((res) => {
				state.menuData = res.data;
			}).catch(() => {
			})
		}

		onMounted(() => {
			loadMenuList();
		});

		return {
			addMenuRef,
			editMenuRef,
			onOpenAddMenu,
			onOpenEditMenu,
			menuTableData,
			onTabelRowDel,
			loadMenuList,
			...toRefs(state),
		};
	},
};
</script>
