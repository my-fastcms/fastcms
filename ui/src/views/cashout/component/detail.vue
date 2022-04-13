<template>
	<div class="system-menu-container">
		<el-dialog title="提现详情" fullscreen v-model="isShowDialog">
			<div class="personal">
				<el-row>
					<el-col :xs="24" :sm="24">
						<el-card shadow="hover">
							<div class="personal-user">
								<div class="personal-user-right">
									<el-row>
										<el-col :span="24" class="personal-title mb18">{{ruleForm.orderTitle}} </el-col>
										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">提现用户：</div>
													<div class="personal-item-value">{{ruleForm.nickName}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">提现金额：</div>
													<div class="personal-item-value">{{ruleForm.amount}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">收款账号：</div>
													<div class="personal-item-value">{{ruleForm.payTo}}</div>
												</el-col>
												
											</el-row>
										</el-col>
										<el-col :span="24">
											<el-row>
												<el-col :xs="24" :sm="8" class="personal-item mb6">
													<div class="personal-item-label">提现时间：</div>
													<div class="personal-item-value">{{ruleForm.created}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">提现状态：</div>
													<div class="personal-item-value">{{ruleForm.statusStr}}</div>
												</el-col>
												<el-col :xs="24" :sm="4" class="personal-item mb6">
													<div class="personal-item-label">审核类型：</div>
													<div class="personal-item-value">{{ruleForm.auditTypeStr}}</div>
												</el-col>
											</el-row>
										</el-col>
										<el-col :span="24" v-if="ruleForm.feedback != null && ruleForm.feedback !=''">
											<el-row>
												<el-col :xs="24" :sm="24" class="personal-item mb6">
													<div class="personal-item-label">拒绝原因：</div>
													<div class="personal-item-value">{{ruleForm.feedback}}</div>
												</el-col>
											</el-row>	
										</el-col>
									</el-row>
								</div>
							</div>
						</el-card>
					</el-col>

					<el-col :span="24">
						<el-card shadow="hover" class="mt5 personal-edit" header="最近收入流水">
							<div class="personal-edit-safe-box" v-for="(v, k) in ruleForm.userAmountStatementList" :key="k">
								<div class="personal-edit-safe-item">
									<div class="personal-edit-safe-item-left">
										<div class="personal-edit-safe-item-left-label">收入 {{ v.changeAmount }} 元</div>
									</div>
									<div class="personal-edit-safe-item-right">
										<el-button type="text"> {{ v.created }}</el-button>
									</div>
								</div>
							</div>
						</el-card>
					</el-col>
				</el-row>
			</div>
			<template #footer>
				<span class="dialog-footer">
                    <el-button type="danger" @click="dialogFormVisible = true" size="small" v-if="ruleForm.status ==0 ? true : false">拒 绝</el-button>
					<el-button type="primary" @click="confirmCashout" size="small" v-if="ruleForm.status ==0 ? true : false">确认提现</el-button>
					<el-button @click="onCancel" size="small">取 消</el-button>
				</span>
			</template>
		</el-dialog>

		<el-dialog v-model="dialogFormVisible" title="拒绝提现申请">
			<el-form :model="form" :rules="rules" ref="myRefForm">
				<el-form-item label="拒绝原因" prop="feedback">
					<el-input v-model="form.feedback" />
				</el-form-item>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="dialogFormVisible = false">取 消</el-button>
					<el-button type="primary" @click="onSubmit">确 认</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { reactive, toRefs, ref, getCurrentInstance, onUpdated } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { getCashoutDetail, confirmCashOut, refuseCashOut } from '/@/api/order/index';
import qs from 'qs';

export default {
	name: 'orderDetail',
	setup(props, ctx) {
		const { proxy } = getCurrentInstance() as any;
		const state = reactive({
			dialogFormVisible: false,
			isShowDialog: false,
			form: {
				feedback: ''
			},
			ruleForm: {
				id: null,
			},
            rules: {
				"feedback": { required: true, message: '请输入拒绝原因', trigger: 'blur' },
			},
		});
		// 打开弹窗
		const openDialog = (id?: object) => {
			state.isShowDialog = true;
			state.ruleForm.id=id;
		};
		// 关闭弹窗
		const closeDialog = () => {
			state.isShowDialog = false;
		};
		// 取消
		const onCancel = () => {
			closeDialog();
			initForm();
		};

		const onSubmit = () => {

			proxy.$refs['myRefForm'].validate((valid: any) => {
				if (valid) {
					refuseCashOut(state.ruleForm.id, qs.stringify(state.form)).then(() => {
						onCancel()
						state.dialogFormVisible = false;
					}).catch(error =>{
						ElMessage.error(error.message);
					})
				}
			});

		};

        //确认提现
        const confirmCashout = () => {
            ElMessageBox.confirm('确认提现申请会立即转账给该用户，请认真核对！', '提示', {
				confirmButtonText: '确认',
				cancelButtonText: '取消',
				type: 'warning',
			}).then(() => {
				confirmCashOut(state.ruleForm.id).then(() => {
					onCancel()
				}).catch(error => {
					ElMessage.error(error.message);
				})
			})
			.catch(() => {});
        }

		const getCashoutDetailInfo = () => {
			if(state.ruleForm.id && state.ruleForm.id != null) {
				getCashoutDetail(state.ruleForm.id).then((res) => {
					state.ruleForm = res.data;
				})
			}
		}

		onUpdated(() => {
			getCashoutDetailInfo();
		});

		// 表单初始化，方法：`resetFields()` 无法使用
		const initForm = () => {
			state.ruleForm = {};
		};

		return {
			openDialog,
			closeDialog,
			onCancel,
			onSubmit,
            confirmCashout,
			...toRefs(state),
		};
	},
};
</script>
<style scoped lang="scss">
@import '../../../theme/mixins/mixins.scss';
.personal {
	.personal-user {
		height: 130px;
		display: flex;
		align-items: center;
		.personal-user-left {
			width: 100px;
			height: 130px;
			border-radius: 3px;
			::v-deep(.el-upload) {
				height: 100%;
			}
			.personal-user-left-upload {
				img {
					width: 100%;
					height: 100%;
					border-radius: 3px;
				}
				&:hover {
					img {
						animation: logoAnimation 0.3s ease-in-out;
					}
				}
			}
		}
		.personal-user-right {
			flex: 1;
			padding: 0 15px;
			.personal-title {
				font-size: 18px;
				@include text-ellipsis(1);
			}
			.personal-item {
				display: flex;
				align-items: center;
				font-size: 13px;
				.personal-item-label {
					color: var(--el-text-color-secondary);
					@include text-ellipsis(1);
				}
				.personal-item-value {
					@include text-ellipsis(1);
				}
			}
		}
	}
	.personal-info {
		.personal-info-more {
			float: right;
			color: var(--el-text-color-secondary);
			font-size: 13px;
			&:hover {
				color: var(--color-primary);
				cursor: pointer;
			}
		}
		.personal-info-box {
			height: 130px;
			overflow: hidden;
			.personal-info-ul {
				list-style: none;
				.personal-info-li {
					font-size: 13px;
					padding-bottom: 10px;
					.personal-info-li-title {
						display: inline-block;
						@include text-ellipsis(1);
						color: var(--el-text-color-secondary);
						text-decoration: none;
					}
					& a:hover {
						color: var(--color-primary);
						cursor: pointer;
					}
				}
			}
		}
	}
	.personal-recommend-row {
		.personal-recommend-col {
			.personal-recommend {
				position: relative;
				height: 100px;
				color: var(--color-whites);
				border-radius: 3px;
				overflow: hidden;
				cursor: pointer;
				&:hover {
					i {
						right: 0px !important;
						bottom: 0px !important;
						transition: all ease 0.3s;
					}
				}
				i {
					position: absolute;
					right: -10px;
					bottom: -10px;
					font-size: 70px;
					transform: rotate(-30deg);
					transition: all ease 0.3s;
				}
				.personal-recommend-auto {
					padding: 15px;
					position: absolute;
					left: 0;
					top: 5%;
					.personal-recommend-msg {
						font-size: 12px;
						margin-top: 10px;
					}
				}
			}
		}
	}
	.personal-edit {
		.personal-edit-title {
			position: relative;
			padding-left: 10px;
			color: var(--el-text-color-regular);
			&::after {
				content: '';
				width: 2px;
				height: 10px;
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				background: var(--color-primary);
			}
		}
		.personal-edit-safe-box {
			border-bottom: 1px solid var(--el-border-color-light, #ebeef5);
			padding: 5px 0;
			.personal-edit-safe-item {
				width: 100%;
				display: flex;
				align-items: center;
				justify-content: space-between;
				.personal-edit-safe-item-left {
					flex: 1;
					overflow: hidden;
					.personal-edit-safe-item-left-label {
						color: var(--el-text-color-regular);
						margin-bottom: 2px;
					}
					.personal-edit-safe-item-left-value {
						color: var(--el-text-color-secondary);
						@include text-ellipsis(1);
						margin-right: 15px;
					}
				}
			}
			&:last-of-type {
				padding-bottom: 0;
				border-bottom: none;
			}
		}
	}
}
</style>