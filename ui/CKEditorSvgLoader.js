const fs = require('fs').promises
module.exports = function svgLoader() {
    const svgRegex = /\.svg(\?ckeditor)?$/;
    return {
        name: 'svg-loader',
        enforce: 'pre',
        async load (path) {
            if (path.match(svgRegex)) {
                const _path = path.split("?");
                if (_path[0].includes("/node_modules/@ckeditor") || _path[1] === "ckeditor") {
                    const svg = await fs.readFile(_path[0], 'utf-8');
                    return `export default ${JSON.stringify(svg)}`;
                }
            }
        }
    }
}
module.exports.default = module.exports
