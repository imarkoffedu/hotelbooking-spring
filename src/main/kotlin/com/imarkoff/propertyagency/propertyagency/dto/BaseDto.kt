package com.imarkoff.propertyagency.propertyagency.dto

interface BaseDto<Entity> {
    fun fromEntity(entity: Entity): BaseDto<Entity>
}