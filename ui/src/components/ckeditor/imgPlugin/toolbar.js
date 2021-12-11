import Plugin from "@ckeditor/ckeditor5-core/src/plugin";
import ButtonView from "@ckeditor/ckeditor5-ui/src/button/buttonview";
import ButtonIcon from "./imageIcon.svg?ckeditor";
import { COMMAND_NAME_IMG, COMMAND_LABEL_IMG } from "./constant";
import connect from "./connect";

export default class ImgToolbarUI extends Plugin {
    init() {
        this.createToolbarButton();
    }
    createToolbarButton() {
        const editor = this.editor;
        const command = editor.commands.get(COMMAND_NAME_IMG);
        connect.editorObj = editor;
        window.dd = editor.model;
        editor.ui.componentFactory.add(COMMAND_NAME_IMG, (locale) => {
            const view = new ButtonView(locale);
            view.set({
                label: COMMAND_LABEL_IMG,
                tooltip: true,
                icon: ButtonIcon,
                // withText: true, // 显示label
                class: "toolbar_button_img_extend",
            });
            // 将按钮的状态关联到命令对应值上
            view.bind("isOn", "isEnabled").to(command, "value", "isEnabled");
            // 点击按钮时触发相应命令
            this.listenTo(view, "execute", () => {
                connect.showWinControl && (connect.showWinControl.value = true);
            });
            return view;
        });
    }
}
