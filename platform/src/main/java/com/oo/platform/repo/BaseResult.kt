package com.oo.platform.repo


open class BaseResult<Data, Error>(
        @param:StatusValue @field:StatusValue val status: Int,
        val data: Data?, val errors: Error?
) {

    val isSuccess: Boolean
        get() = status == StatusValue.SUCCESS

    val isLoading: Boolean
        get() = status == StatusValue.LOADING

    override fun toString(): String {
        return "Result{" +
                "status=" + status +
                ", data=" + data +
                ", errors=" + errors +
                '}'.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val result = other as BaseResult<*, *>?

        if (status != result!!.status) return false
        if (if (data != null) data != result.data else result.data != null) return false
        return if (errors != null) errors == result.errors else result.errors == null
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + (errors?.hashCode() ?: 0)
        return result
    }

}
