<template>
	<div class="personal">
		<el-row>
			<!-- 设置信息 -->
			<el-col :span="24">
				<el-card shadow="hover">
					
					<el-form :model="ruleForm" :rules="rules" ref="myRefForm" size="small" label-width="150px" class="mt35 mb35">
                        
						<div class="personal-edit-title mb15">文件服务器</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="域名设置" prop="file_domain">
									<el-input v-model="ruleForm.file_domain" placeholder="请输入文件服务器域名" clearable></el-input>
								</el-form-item>
							</el-col>
						</el-row>


                        <div class="personal-edit-title mb15">文件大小限制</div>

						<el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="图片大小设置" prop="imageMaxSize">
									<el-input v-model="ruleForm.imageMaxSize" placeholder="请输入允许上传图片的最大值，单位MB" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
									<div class="sub-title">单位M，0表示不限制文件大小</div>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="其他文件大小设置" prop="otherMaxSize">
									<el-input v-model="ruleForm.otherMaxSize" placeholder="请输入允许上传其他文件的最大值，单位MB" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
									<div class="sub-title">单位M，0表示不限制文件大小</div>
								</el-form-item>
							</el-col>
						</el-row>

                        <div class="personal-edit-title mb15">水印设置</div>
                        <el-row :gutter="35">
							<el-col class="mb20">
								<el-form-item label="是否开启水印">
									<el-switch
										v-model="ruleForm.enableWatermark"
										active-color="#13ce66">
									</el-switch>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="水印位置">
									<el-select v-model="ruleForm.waterMarkPos" placeholder="请选择" class="w100">
										<el-option
											v-for="item in posOptions"
											:key="item.value"
											:label="item.label"
											:value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col class="mb20">
								<el-form-item label="透明度">
									<el-input v-model="ruleForm.diaphaneity" placeholder="请输入透明度" onkeyup="value=value.replace(/[^\d]/g,'')" clearable></el-input>
								</el-form-item>
							</el-col>
							<!-- <el-col class="mb20">
								<el-form-item label="水印文字">
									<el-input v-model="ruleForm.waterMarkTxt" placeholder="请输入水印文字" clearable></el-input>
								</el-form-item>
							</el-col> -->
							<el-col class="mb20">
								<el-form-item label="水印图片">
									<el-image
										style="width: 100px; height: 100px"
										:src="ruleForm.waterMarkFile"
										:fit="fit"></el-image>
								</el-form-item>
								<el-form-item>
									<el-link type="primary" @click="onAttachDialogOpen">选择图片</el-link>
									<div class="sub-title">上传水印图片请先关闭水印功能</div>
								</el-form-item>
							</el-col>
						</el-row>
                        <el-col>
                            <el-form-item>
                                <el-button type="primary"  @click="onSubmit" icon="el-icon-position">保 存</el-button>
                            </el-form-item>
						</el-col>
					</el-form>
				</el-card>
			</el-col>
		</el-row>
		<AttachDialog ref="attachDialogRef" @attachHandler="getSelectAttach" :fileType="fileType"/>
	</div>
</template>

<script lang="ts">
import { toRefs, ref, reactive, computed, getCurrentInstance, onMounted } from 'vue';
import { formatAxis } from '/@/utils/formatTime';
import { ElMessage } from 'element-plus';
import qs from 'qs';
import { saveConfig, getConfigList } from '/@/api/config/index';
import AttachDialog from '/@/components/attach/index.vue';

export default {
	name: 'attachSet',
	components: { AttachDialog },
	setup() {
		const attachDialogRef = ref();
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			fileType: "image",
			fit: "fill",
			posOptions: [{
				value: 'leftup',
				label: '左上'
			}, {
				value: 'rightup',
				label: '右上'
			}, {
				value: 'middle',
				label: '中间'
			}, {
				value: 'leftdown',
				label: '左下'
			}, {
				value: 'rightdown',
				label: '右下'
			}],
			ruleForm: {
				file_domain: '',
				imageMaxSize: 0,
				otherMaxSize: 0,
				enableWatermark: true,
				waterMarkPos: 'middle',
				diaphaneity: 0,
				waterMarkTxt: '',
				waterMarkFile: ''
			},
			rules: {
				"imageMaxSize": [
					{required: true, message: '请输入允许上传图片大小最大值', trigger: 'blur'},
				],
				"otherMaxSize":[
					{required: true, message: '请输入允许上传其他文件大小最大值', trigger: 'blur'},
				],
				"file_domain": [
					{required: true, message: '请输入文件服务器域名', trigger: 'blur'},
				]
			}
		});

		// 当前时间提示语
		const currentTime = computed(() => {
			return formatAxis(new Date());
		});

		const onSubmit = () => {
			proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					let params = qs.stringify(state.ruleForm, {arrayFormat: 'repeat'});
					saveConfig(params).then(() => {
						ElMessage.success("保存成功")
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});
		};

		//获取弹出框选中的附件
		const getSelectAttach = (value) => {
			state.ruleForm.waterMarkFile = value[0].path;
		};

		//打开附件弹出框
		const onAttachDialogOpen = () => {
			attachDialogRef.value.openDialog(1);
		};

		onMounted(() => {
			let paramKeys = new Array();
			const keys: any[] = Object.keys(state.ruleForm);
			keys.forEach(key => { paramKeys.push(key); });
			let params = qs.stringify( {"configKeys" : paramKeys}, {arrayFormat: 'repeat'});
			getConfigList(params).then((res) => {
				res.data.forEach(item => {
					state.ruleForm[item.key] = item.jsonValue;
				});
			});
		});

		return {
			getSelectAttach,
			attachDialogRef,
			currentTime,
			onSubmit,
			onAttachDialogOpen,
			...toRefs(state),
		};
	},
};
</script>

<style scoped lang="scss">
@import '../../theme/mixins/mixins.scss';
</style>
