import globals from "globals";
import pluginJs from "@eslint/js";


/** @type {import('eslint').Linter.FlatConfig[]} */
export default [
    {
        ignores: ["**/*.config.js", "**/*.config.mjs", "src/**/flowbite.min.js"], // 除外ファイル
    },
    pluginJs.configs.recommended, // 推奨設定
    {
        languageOptions: {
            globals: {
                ...globals.browser,
                google: "readonly", // google をグローバル変数として登録
                Modal: "readonly" // Modal をグローバル変数として登録
            },
        },
        files: ["src/**/*.js"], // 適用ファイル
        rules: {
            semi: "error", // セミコロン必須
            "eqeqeq": "error", // 常に === と !== を使用する
            "no-undef": "warn", // 未定義の変数を警告
            "no-unused-vars": "warn", // 未使用の変数を警告
            "no-console": "warn", // console.log を警告 (必要に応じて "off" に変更)
            "quotes": ["error", "single", { "avoidEscape": true }], // シングルクォートを強制

            "no-mixed-spaces-and-tabs": "warn", // スペースとタブの混在を禁止
            "indent": ["error", 4], // インデントをスペース4つに統一
            "no-multiple-empty-lines": ["error", { "max": 1 }], // 複数行の空行を禁止
            "eol-last": ["error", "always"], // ファイルの末尾に改行を強制
            "no-trailing-spaces": "error", // 行末の余分なスペースを禁止
            "space-before-function-paren": ["error", "never"], // 関数の括弧前にスペースを禁止

            "prefer-const": "error", // 再代入しない変数は const を強制
            "arrow-spacing": ["error", { "before": true, "after": true }], // アロー関数の前後にスペースを強制
            "no-var": "error", // var を禁止 (let または const を使用)
        },
    },
];