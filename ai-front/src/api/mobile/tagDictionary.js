import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/mobile/"

export function getTagDictionaryPageList(data) {
  return get(prefix + 'tagDictionary/getTagDictionaryPageList', data)
}

export function generateTagDictionary(data) {
  return post(prefix + 'tagDictionary/generateTagDictionary', data)
}

export function changeTagDictionaryById(data) {
  return put(prefix + 'tagDictionary/changeTagDictionaryById', data)
}

export function expurgateTagDictionaryById(data) {
  return delWithParams(prefix + 'tagDictionary/expurgateTagDictionaryById', data)
}

export function expurgateTagDictionaryBatch(data) {
  return delWithParams(prefix + 'tagDictionary/expurgateTagDictionaryBatch', data)
}

export function getTagDictionaryById(data) {
  return get(prefix + 'tagDictionary/getTagDictionaryById', data)
}