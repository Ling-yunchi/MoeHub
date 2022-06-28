const { defineConfig } = require("@vue/cli-service");
const Components = require("unplugin-vue-components/webpack");
const { ArcoResolver } = require("unplugin-vue-components/resolvers");

module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    plugins: [
      Components({
        resolvers: [ArcoResolver()],
      }),
    ],
  },
  devServer: {
    port: 4000,
    proxy: {
      "/api/": {
        target: "http://localhost:8080",
        secure: false,
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
});
