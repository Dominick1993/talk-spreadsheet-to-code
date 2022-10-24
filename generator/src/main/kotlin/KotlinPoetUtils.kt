package dev.labuda.spreadsheets

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.reflect.KClass

object KotlinPoetUtils {

    fun createDataClassWithPrimaryConstructorFields(
        className: ClassName,
        fieldsWithType: List<Pair<String, KClass<*>>>,
    ) =
        TypeSpec.classBuilder(className)
            .addModifiers(KModifier.DATA)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .also { funSpec ->
                        fieldsWithType.forEach { (name, kClass) ->
                            funSpec.addParameter(name, kClass)
                        }
                    }
                    .build()
            )
            .also { typeSpec ->
                fieldsWithType.forEach { (name, kClass) ->
                    typeSpec.addProperty(
                        PropertySpec.builder(name, kClass)
                            .initializer(name)
                            .build()
                    )
                }
            }
            .build()

    inline fun <reified T> FunSpec.Builder.addWhenStatement(
        fieldName: String,
        whenBranchSpec: (FunSpec.Builder) -> Unit,
    ) =
        this.addParameter(fieldName, T::class)
            .beginControlFlow("return when ($fieldName)")
            .also { funSpec ->
                whenBranchSpec(funSpec)
            }
            .beginControlFlow("else ->")
            .addStatement("error(\"Unknown $fieldName='\$$fieldName' was provided\")")
            .endControlFlow()
            .endControlFlow()
}
