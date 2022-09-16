import { findOptimalInsertionRange } from "@ckeditor/ckeditor5-widget/src/utils";

export default function insertImage(model, attributes = {}) {
    console.log(model);
    if (!attributes || !attributes.src) {
        return;
    }
    model.change((writer) => {
        const imageElement = writer.createElement("imageInline", attributes);
        // 使用 findOptimalInsertionPosition 方法来获取最佳位置
        // 如果某个选择位于段落的中间，则将返回该段落之前的位置，不拆分当前段落
        // 如果选择位于段落的末尾，则将返回该段落之后的位置
        const insertAtSelection = findOptimalInsertionRange(
            model.document.selection,
            model
        );
        model.insertContent(imageElement, insertAtSelection);
    });
}
