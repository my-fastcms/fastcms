import Command from "@ckeditor/ckeditor5-core/src/command";
import { SCHEMA_NAME_IMG } from "./constant";

export default class ImgCommand extends Command {
    constructor(editor) {
        super(editor);
        this.attributeKey = SCHEMA_NAME_IMG;
    }
    refresh() {
        const model = this.editor.model;
        const selection = model.document.selection;
        // 校验选中的 Schema 是否允许 IMG 属性，若不允许则禁用按钮
        this.isEnabled = model.schema.checkAttributeInSelection(
            selection,
            this.attributeKey
        );
    }
}
