import { get, post, put, delWithParams } from '@/utils/request'

const prefix = "/api/mobile/"

export function getRecognitionTypePageList(data) {
  return get(prefix + 'recognitionType/getRecognitionTypePageList', data)
}

export function getRecognitionTypes(data) {
  return get(prefix + 'recognitionType/getRecognitionTypes', data)
}

export function generateRecognitionType(data) {
  return post(prefix + 'recognitionType/generateRecognitionType', data)
}

export function changeRecognitionTypeById(data) {
  return put(prefix + 'recognitionType/changeRecognitionTypeById', data)
}

export function expurgateRecognitionTypeById(data) {
  return delWithParams(prefix + 'recognitionType/expurgateRecognitionTypeById', data)
}

export function expurgateRecognitionTypeBatch(data) {
  return delWithParams(prefix + 'recognitionType/expurgateRecognitionTypeBatch', data)
}

export function getRecognitionTypeById(data) {
  return get(prefix + 'recognitionType/getRecognitionTypeById', data)
}