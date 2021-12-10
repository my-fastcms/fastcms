<template>
	<div>
		<el-card shadow="hover">
			<div class="mb15">
				<el-upload 
					class="upload-btn"
					:action="uploadUrl"
					name="file"
					multiple
					:headers="headers"
					:show-file-list="false"
					:on-success="uploadSuccess"
					:on-error="onHandleUploadError"
					:limit="limit">
					<el-button class="mt15" size="small" @click="addArticle()" type="primary" icon="iconfont icon-shuxingtu">安装模板</el-button>
				</el-upload>
			</div>
			<el-table :data="tableData" stripe style="width: 100%">
				<el-table-column prop="id" label="ID" show-overflow-tooltip></el-table-column>
				<el-table-column prop="name" label="模板名称" show-overflow-tooltip></el-table-column>
				<el-table-column prop="path" label="模板路径" show-overflow-tooltip></el-table-column>
				<el-table-column prop="version" label="版本" show-overflow-tooltip></el-table-column>
				<el-table-column prop="provider" label="作者" show-overflow-tooltip></el-table-column>
				<el-table-column prop="description" label="描述" show-overflow-tooltip></el-table-column>
				<el-table-column label="操作" width="90">
					<template #default="scope">
						<el-button v-if="scope.row.id != 1" size="mini" type="text" @click="onRowUnInstall(scope.row)">卸载</el-button>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
	</div>
</template>

<script lang="ts">
import { ElMessageBox, ElMessage } from 'element-plus';
import { toRefs, reactive, onMounted } from 'vue';
import { getTemplateList, unInstallTemplate } from '/@/api/template/index';
import { Session } from '/@/utils/storage';
export default {
	name: 'articleManager',
	setup() {
		const state = reactive({
			tableData: [],
			limit: 1,
			uploadUrl: import.meta.env.VITE_API_URL + "/admin/template/install",
			headers: {"Authorization": Session.get('token')},
		});

		// 初始化表格数据
		const initTableData = () => {
			getTemplateList().then((res) => {
				state.tableData = res.data;
			}).catch(() => {
			})
		};
		// 当前行删除
		const onRowUnInstall = (row: object) => {
			ElMessageBox.confirm('此操作将卸载此['+row.id+']模板, 是否继续?', '提示', {
				confirmButtonText: '卸载',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				unInstallTemplate(row.id).then(() => {
					ElMessage.success("卸载成功");
					initTableData();
				}).catch((res) => {
					ElMessage.error(res.message);
				})
			})
			.catch((res) => {
				ElMessage.error(res.message);
			});
		};

		const uploadSuccess = () => {
            ElMessage.success("上传成功");
			initTableData();
		}
		const onHandleUploadError = () => {
			ElMessage.error("上传失败");
		}
        
		// 页面加载时
		onMounted(() => {
			initTableData();
		});
		return {
            onRowUnInstall,
			initTableData,
			uploadSuccess,
			onHandleUploadError,
			...toRefs(state),
		};
	},
};
</script>

