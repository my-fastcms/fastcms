import Plugin from '@ckeditor/ckeditor5-core/src/plugin';
import ToolbarUI from './toolbar';
import ImgEditing from './editing';
import { COMMAND_NAME_IMG } from './constant';

export default class IMG extends Plugin {
    static get requires() {
        return [ ImgEditing, ToolbarUI ];
    }
    static get pluginName() {
        return COMMAND_NAME_IMG;
    }
}
