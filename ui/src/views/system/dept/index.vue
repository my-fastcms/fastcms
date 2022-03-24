<template>
	<div class="system-dept-container">
		<el-card shadow="hover">
			<div class="system-dept-search mb15">
				<el-button size="small" type="primary" class="mt15" @click="onOpenAddDept" icon="iconfont icon-shuxingtu">新增部门</el-button>
				<el-input size="small" class="ml10" placeholder="请输入部门名称" style="max-width: 180px"> </el-input>
				<el-button size="small" type="primary" class="ml10">查询</el-button>
			</div>
			<el-table
				:data="tableData.data"
				style="width: 100%"
				row-key="id"
				default-expand-all
				:tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
			>
				<el-table-column prop="deptName" label="部门名称" show-overflow-tooltip> </el-table-column>
				<el-table-column prop="status" label="部门状态" show-overflow-tooltip>
					<template #default="scope">
						<el-tag type="success" v-if="scope.row.status">启用</el-tag>
						<el-tag type="info" v-else>禁用</el-tag>
					</template>
				</el-table-column>
				<el-table-column prop="deptDesc" label="部门描述" show-overflow-tooltip></el-table-column>
				<el-table-column prop="created" label="创建时间" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" show-overflow-tooltip width="140">
					<template #default="scope">
						<el-button size="small" type="text" @click="onOpenAddDept(scope.row)">新增</el-button>
						<el-button size="small" type="text" @click="onOpenEditDept(scope.row)">修改</el-button>
						<el-button size="small" type="text" @click="onTabelRowDel(scope.row)">删除</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
		<AddDept ref="addDeptRef" @reloadTable="initTableData"/>
		<EditDept ref="editDeptRef" @reloadTable="initTableData"/>
	</div>
</template>

<script lang="ts">
import { ref, toRefs, reactive, onMounted, defineComponent } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getDeptList, delDept } from '/@/api/dept/index';
import AddDept from '/@/views/system/dept/component/addDept.vue';
import EditDept from '/@/views/system/dept/component/editDept.vue';

// 定义接口来定义对象的类型
interface TableDataRow {
	deptName: string;
	created: string;
	status: boolean;
	sortNum: number;
	deptDesc: string;
	id: number;
	children?: TableDataRow[];
}
interface TableDataState {
	tableData: {
		data: Array<TableDataRow>;
		loading: boolean;
	};
}

export default defineComponent({
	name: 'systemDept',
	components: { AddDept, EditDept },
	setup() {
		const addDeptRef = ref();
		const editDeptRef = ref();
		const state = reactive<TableDataState>({
			tableData: {
				data: [],
				loading: false,
			},
		});
		// 初始化表格数据
		const initTableData = () => {
			getDeptList().then(res => {
				state.tableData.data = res.data
			})
		};
		// 打开新增菜单弹窗
		const onOpenAddDept = (row: TableDataRow) => {
			addDeptRef.value.openDialog(row);
		};
		// 打开编辑菜单弹窗
		const onOpenEditDept = (row: TableDataRow) => {
			editDeptRef.value.openDialog(row);
		};
		// 删除当前行
		const onTabelRowDel = (row: TableDataRow) => {
			ElMessageBox.confirm(`此操作将永久删除部门：${row.deptName}, 是否继续?`, '提示', {
				confirmButtonText: '删除',
				cancelButtonText: '取消',
				type: 'warning',
			})
				.then(() => {
					delDept(row.id).then((res) => {
						if(res.code == 200) {
							ElMessage.success('删除成功');
							initTableData();
						}
					})
				})
				.catch(() => {});
		};
		// 页面加载时
		onMounted(() => {
			initTableData();
		});
		return {
			initTableData,
			addDeptRef,
			editDeptRef,
			onOpenAddDept,
			onOpenEditDept,
			onTabelRowDel,
			...toRefs(state),
		};
	},
});
</script>
