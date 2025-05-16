const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  },  configureWebpack: {
    plugins: [
      new (require('webpack').DefinePlugin)({
        '__VUE_PROD_HYDRATION_MISMATCH_DETAILS__': false,
      }),
    ],
  },
})
