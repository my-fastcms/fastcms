import Plugin from "@ckeditor/ckeditor5-core/src/plugin";
import Widget from '@ckeditor/ckeditor5-widget/src/widget';
import ImgCommand from "./command";
import { COMMAND_NAME_IMG, SCHEMA_NAME_IMG } from "./constant";

export default class ImgEditing extends Plugin {
    static get requires() {
        return [Widget];
    }
    static get pluginName() {
        return "ImgEditing";
    }
    init() {
        const editor = this.editor;
        this.defineSchema();
        editor.commands.add(COMMAND_NAME_IMG, new ImgCommand(editor));
    }
    // 注册 schema
    defineSchema() {
        const schema = this.editor.model.schema;
        schema.extend("$text", { allowAttributes: SCHEMA_NAME_IMG });
    }
}
