<template>
	<div class="personal">
		<el-row>
			<!-- 个人信息 -->
			<el-col :xs="24" :sm="16">
				<el-card shadow="hover" header="个人信息">
					<div class="personal-user">
						<div class="personal-user-left">
							<el-upload class="h100 personal-user-left-upload" action="https://jsonplaceholder.typicode.com/posts/" multiple :limit="1">
								<img :src="getUserInfos.photo" />
							</el-upload>
						</div>
						<div class="personal-user-right">
							<el-row>
								<el-col :span="24" class="personal-title mb18">{{ currentTime }}，{{ getUserInfos.username }}，
								生活变的再糟糕，也不妨碍我变得更好！ 
								</el-col>
								<el-col :span="24">
									<el-row>
										<el-col :xs="24" :sm="8" class="personal-item mb6">
											<div class="personal-item-label">昵称：</div>
											<div class="personal-item-value">{{ personalForm.nickName }}</div>
										</el-col>
										<el-col :xs="24" :sm="16" class="personal-item mb6">
											<div class="personal-item-label">签名：</div>
											<div class="personal-item-value">{{ personalForm.autograph }}</div>
										</el-col>
									</el-row>
								</el-col>
								<!-- <el-col :span="24">
									<el-row>
										<el-col :xs="24" :sm="8" class="personal-item mb6">
											<div class="personal-item-label">登录IP：</div>
											<div class="personal-item-value">192.168.1.1</div>
										</el-col>
										<el-col :xs="24" :sm="16" class="personal-item mb6">
											<div class="personal-item-label">登录时间：</div>
											<div class="personal-item-value">2021-02-05 18:47:26</div>
										</el-col>
									</el-row>
								</el-col> -->
							</el-row>
						</div>
					</div>
				</el-card>
			</el-col>

			<!-- 消息通知 -->
			<el-col :xs="24" :sm="8" class="pl15 personal-info">
				<el-card shadow="hover">
					<template #header>
						<span>消息通知</span>
						<span class="personal-info-more">更多</span>
					</template>
					<div class="personal-info-box">
						<ul class="personal-info-ul">
							<li v-for="(v, k) in newsInfoList" :key="k" class="personal-info-li">
								<a :href="v.link" target="_block" class="personal-info-li-title">{{ v.title }}</a>
							</li>
						</ul>
					</div>
				</el-card>
			</el-col>

			<!-- 更新信息 -->
			<el-col :span="24">
				<el-card shadow="hover" class="mt15 personal-edit" header="更新信息">
					<div class="personal-edit-title">基本信息</div>
					<el-form :model="personalForm" :rules="personalFormRules" ref="myRefPersonalForm" size="small" label-width="80px" class="mt35 mb35">
						<el-row :gutter="35">
							<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
								<el-form-item label="昵称" prop="nickName">
									<el-input v-model="personalForm.nickName" placeholder="请输入昵称" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
								<el-form-item label="邮箱" prop="email">
									<el-input v-model="personalForm.email" placeholder="请输入邮箱" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
								<el-form-item label="手机" prop="mobile">
									<el-input v-model="personalForm.mobile" placeholder="请输入手机" clearable></el-input>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="12" :md="8" :lg="6" :xl="4" class="mb20">
								<el-form-item label="性别" prop="sex">
									<el-select v-model="personalForm.sex" placeholder="请选择性别" clearable class="w100">
										<el-option label="男" value="1"></el-option>
										<el-option label="女" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="12" :md="8" :lg="12" :xl="12" class="mb20">
								<el-form-item label="签名" prop="autograph">
									<el-input v-model="personalForm.autograph" placeholder="请输入签名" clearable></el-input>
								</el-form-item>
							</el-col>
							
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item>
									<el-button type="primary" @click="onUpdateUserInfo" icon="el-icon-position">更新个人信息</el-button>
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>
					<div class="personal-edit-title mb15">账号安全</div>
					<div class="personal-edit-safe-box">
						<div class="personal-edit-safe-item">
							<div class="personal-edit-safe-item-left">
								<div class="personal-edit-safe-item-left-label">账户密码</div>
								<div class="personal-edit-safe-item-left-value">当前密码强度：强</div>
							</div>
							<div class="personal-edit-safe-item-right">
								<el-button type="text"  @click="dialogFormVisible = true">立即修改</el-button>
							</div>
						</div>
					</div>
				</el-card>
			</el-col>
		</el-row>

		<el-dialog v-model="dialogFormVisible" title="修改密码">
			<el-form :model="passwordForm" size="small" label-width="80px" :rules="passwordFormRules" ref="myRefPasswordForm">
				<el-row :gutter="35">
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="旧密码" prop="oldPassword">
							<el-input type="password" v-model="passwordForm.oldPassword" placeholder="请输入旧密码" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="新密码" prop="password">
							<el-input type="password" v-model="passwordForm.password" placeholder="请输入新密码" clearable></el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="mb20">
						<el-form-item label="确认密码" prop="confirmPassword">
							<el-input type="password" v-model="passwordForm.confirmPassword" placeholder="请再次输入新密码" clearable></el-input>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<template #footer>
			<span class="dialog-footer">
				<el-button @click="dialogFormVisible = false">取 消</el-button>
				<el-button type="primary" @click="onUpdatePassword">确 定</el-button>
			</span>
			</template>
		</el-dialog>
	</div>
</template>

<script lang="ts">
import { toRefs, reactive, computed, getCurrentInstance, onMounted } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { formatAxis } from '/@/utils/formatTime';
import { newsInfoList, recommendList } from './mock';
import { updatePassword } from '/@/api/user/index';
import { updateUser, getUserInfo } from '/@/api/user/client';
import { Local, Session } from '/@/utils/storage';
import { useStore } from '/@/store/index';
import qs from 'qs';

export default {
	name: 'centerPersonal',
	setup() {
		const { proxy } = getCurrentInstance() as any;
		const store = useStore();
		const state = reactive({
			dialogFormVisible: false,
			formLabelWidth: '120px',
			newsInfoList,
			recommendList,
			passwordForm: {
				oldPassword: '',
				password: '',
				confirmPassword: ''
			},
			passwordFormRules: {
				"oldPassword": { required: true, message: '请输入旧密码', trigger: 'blur' },
				"password": { required: true, message: '请输入新密码', trigger: 'blur' },
				"confirmPassword": { required: true, message: '请再次输入新密码', trigger: 'blur' },
			},
			personalForm: {
				nickName: '',
				email: '',
				mobile: '',
				sex: '',
				autograph: '',
			},
			personalFormRules: {
				"nickName": { required: true, message: '请输入昵称', trigger: 'blur' },
				"email": { required: true, message: '请输入邮箱地址', trigger: 'blur' },
				"autograph": { required: true, message: '请输入个性签名', trigger: 'blur' },
			},
		});

		const onUpdateUserInfo = () => {
			proxy.$refs['myRefPersonalForm'].validate((valid: any) => {
				
				if (valid) {
					let params = qs.stringify(state.personalForm, {arrayFormat: 'repeat'});
					console.info("doUpdate User info")
					updateUser(params).then(() => {
						ElMessage.success("保存成功")
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});

		}

		// 关闭弹窗
		const closeDialog = () => {
			state.dialogFormVisible = false; 
			state.passwordForm.password = "";
			state.passwordForm.confirmPassword = "";
		}

		const onUpdatePassword = () => {

			proxy.$refs['myRefPasswordForm'].validate((valid: any) => {
				
				if (valid) {
					let params = qs.stringify(state.passwordForm, {arrayFormat: 'repeat'});
					updatePassword(params).then(() => {
						closeDialog();
						Session.clear(); // 清除浏览器全部临时缓存
						Local.clear();
						ElMessageBox.alert('密码修改成功，请重新登录', '提示', {})
							.then(() => {
								window.location.href = '/'; // 去登录页
							})
							.catch(() => {});
					}).catch((res) => {
						ElMessage({showClose: true, message: res.message ? res.message : '系统错误' , type: 'error'});
					})
				}
			});

		};

		// 当前时间提示语
		const currentTime = computed(() => {
			return formatAxis(new Date());
		});
		// 获取用户信息 vuex
		const getUserInfos = computed(() => {
			return store.state.userInfos.userInfos;
		});

		onMounted(() => {
			getUserInfo().then((res => {
				state.personalForm.nickName = res.data.nickName;
				state.personalForm.email = res.data.email;
				state.personalForm.mobile = res.data.mobile;
				state.personalForm.autograph = res.data.autograph;
				state.personalForm.sex = res.data.sex + "";
			})).catch(()=>{})
		})

		return {
			getUserInfos,
			onUpdateUserInfo,
			onUpdatePassword,
			currentTime,
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
			padding: 15px 0;
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
						margin-bottom: 5px;
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
