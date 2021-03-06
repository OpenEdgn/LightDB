package edgn.lightdb.jedis.internal.data.map

import edgn.lightdb.api.structs.map.LightMap
import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.jedis.options.JedisLightDBConfig
import edgn.lightdb.jedis.options.JedisPool
import java.util.Optional
import kotlin.reflect.KClass

class JedisMapGroup(
    private val name: String,
    private val pool: JedisPool,
    private val config: JedisLightDBConfig
) : LightMapGroup {

    override fun <K : Any, V : Any> get(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Optional<out LightMap<K, V>> = pool.session {
        val keyCover = keyCover(key)
        if (it.exists(keyCover).not()) {
            Optional.empty()
        } else {
            Optional.of(
                JedisMapValue(
                    pool = pool,
                    config = config,
                    groupKey = keyCover,
                    keyType = keyType,
                    valueType = valueType
                )
            )
        }
    }

    override fun <K : Any, V : Any> getOrCreate(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): LightMap<K, V> = pool.session {
        val keyCover = keyCover(key)
        JedisMapValue(
            pool = pool,
            config = config,
            groupKey = keyCover,
            keyType = keyType,
            valueType = valueType
        )
    }

    override fun drop(key: String): Boolean = pool.session {
        it.del(keyCover(key)) > 0
    }

    override fun exists(key: String): Boolean = pool.session {
        it.exists(keyCover(key))
    }

    private fun keyCover(key: String): String {
        return "${config.redisHeader}hash:$name:$key"
    }
}
