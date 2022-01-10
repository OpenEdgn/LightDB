package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.api.structs.list.LightList
import edgn.lightdb.memory.internal.utils.IDataModules
import edgn.lightdb.memory.internal.utils.MemoryMeta
import java.util.Optional
import java.util.Vector
import kotlin.reflect.KClass

class MemListValue<V : Any>(
    key: String,
    override val valueType: KClass<V>
) : LightList<V>, IDataModules {

    override val meta: MemoryMeta = MemoryMeta(key)

    private val container = Vector<V>()

    override val size: Long
        get() = container.size.toLong()

    override val available: Boolean
        get() = meta.available

    override fun clear() {
        container.clear()
    }

    override fun add(element: V): Unit = meta.checkAvailable {
        container.add(element)
    }

    override fun add(index: Long, element: V) = meta.checkAvailable {
        container.add(index.toInt(), element)
    }

    override fun remove(index: Long): Optional<V> = meta.checkAvailable {
        try {
            Optional.ofNullable(container.removeAt(index.toInt()))
        } catch (e: Exception) {
            Optional.empty()
        }
    }

    override fun set(index: Long, element: V): V = meta.checkAvailable {
        container.set(index.toInt(), element)
    }

    override fun get(index: Long): Optional<V> = meta.checkAvailable {
        Optional.ofNullable(container[index.toInt()])
    }

    override fun indexOf(element: V): Long = meta.checkAvailable {
        container.indexOf(element).toLong()
    }

    override fun lastIndexOf(element: V): Long = meta.checkAvailable {
        container.lastIndexOf(element).toLong()
    }

    override fun values(): Iterator<V> = meta.checkAvailable {
        container.iterator()
    }
}