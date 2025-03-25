package com.notes.configs;

import com.notes.mappers.*;
import com.notes.mappers.AccountMapper;
import com.notes.mappers.CaseMapper;
import com.notes.mappers.GeneralAccountMapper;
import com.notes.mappers.InventoryMapper;
import com.notes.mappers.ItemMapper;
import com.notes.mappers.MoneyMapper;
import com.notes.mappers.MoneyTypeMapper;
import com.notes.mappers.PageMapper;
import com.notes.mappers.SlotMapper;
import com.notes.mappers.TwistMapper;
import com.notes.mappers.TwistMarkMapper;
import org.mapstruct.MapperConfig;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValueMappingStrategy.RETURN_NULL;
import static org.mapstruct.ReportingPolicy.ERROR;
import static org.mapstruct.SubclassExhaustiveStrategy.COMPILE_ERROR;

@MapperConfig(componentModel = "spring",
        injectionStrategy = CONSTRUCTOR,
        nullValueCheckStrategy = ALWAYS,
        nullValueMappingStrategy = RETURN_NULL,
        unmappedTargetPolicy = ERROR,
        subclassExhaustiveStrategy = COMPILE_ERROR,

        uses = {AccountMapper.class, GeneralAccountMapper.class,
                InventoryMapper.class, InventoryMapper.class, MoneyMapper.class,
                MoneyTypeMapper.class, PageMapper.class, SlotMapper.class, ItemMapper.class,
                TwistMapper.class, TwistMarkMapper.class, CaseMapper.class})
public interface MapstructConfig {
}
