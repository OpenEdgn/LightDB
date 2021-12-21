package edgn.lightdb.api.utils

/**
 * 环境配置加载器
 */
class ConfigLoader(private val source: Map<String, String>) {
    /**
     * 获取String配置
     */
    fun getString(key: String, defaultValue: String): String {
        return source.getOrDefault(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return source.getOrDefault(key, defaultValue.toString()).toIntOrNull() ?: defaultValue
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return source.getOrDefault(key, defaultValue.toString()).toLongOrNull() ?: defaultValue
    }
}
