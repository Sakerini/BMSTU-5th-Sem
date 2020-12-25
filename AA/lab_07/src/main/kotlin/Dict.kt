class Dict<V>(private val entries: ArrayList<Entry<String, V>>) {
    private val segments = ArrayList<SegmentChar>()

    init {
        for (i in 'a'.toInt()..'z'.toInt()) {
            val segmentChar = SegmentChar(i.toChar(), -1, -1)
            segments.add(segmentChar)
        }

        initializeBorders()

    }

    private fun initializeBorders() {
        var i = 0
        var sortedEntry = entries.sortedBy { it.key }
        for (j in 0 until segments.size) {
            var curSeg = segments[j]
            while (i < sortedEntry.size - 1) {
                if (curSeg.char == sortedEntry[i].key[0]) {
                    curSeg.left = i
                } else {
                    break
                }
                while (curSeg.char == sortedEntry[i].key[0]) {
                    i++
                }
                curSeg.right = i
            }
        }
    }

    fun segmentSearch(key: String): V? {
        var left = -1
        var right = -1
        for (segment in segments) {
            if (segment.char === key[0]) {
                left = segment.left
                right = segment.right
                break
            }
        }

        var sortedList = entries.sortedBy { it.key }

        return binarySearch(sortedList, key, left, right);
    }

    fun get(key: String): V? {
        entries.forEach { entry ->
            if (entry.key == key) {
                return entry.value
            }
        }

        return null
    }

    fun sortedGet(key: String): V? {
        var sortedList = entries.sortedBy { it.key }
        return binarySearch(sortedList, key, 0, sortedList.size - 1)
    }

    private fun binarySearch(sortedList: List<Entry<String, V>>, key: String, min: Int, max: Int): V? {
        var left = min
        var middle = min
        var right = max

        while (sortedList[middle].key.compareTo(key) != 0 && left < right) {
            if (key > sortedList[middle].key) {
                left = middle + 1
            } else {
                right = middle - 1
            }

            middle = (left + right) / 2
        }

        if (left > right) {
            return null
        }

        return sortedList[middle].value
    }


    private data class SegmentChar(val char: Char, var left: Int, var right: Int)
}